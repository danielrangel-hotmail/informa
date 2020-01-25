package com.objective.informa.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.objective.informa.domain.PushSubscription;
import com.objective.informa.domain.User;
import com.objective.informa.repository.PushSubscriptionRepository;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.security.SecurityUtils;
import com.objective.informa.service.dto.PushSubscriptionDTO;
import com.objective.informa.service.mapper.PushSubscriptionMapper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PushSubscription}.
 */
@Service
@Transactional
public class PushSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(PushSubscriptionService.class);

    private final PushSubscriptionRepository pushSubscriptionRepository;

    private final PushSubscriptionMapper pushSubscriptionMapper;

    private final UserRepository userRepository;

    public PushSubscriptionService(PushSubscriptionRepository pushSubscriptionRepository,
        PushSubscriptionMapper pushSubscriptionMapper,
        UserRepository userRepository) {
        this.pushSubscriptionRepository = pushSubscriptionRepository;
        this.pushSubscriptionMapper = pushSubscriptionMapper;
        this.userRepository = userRepository;
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public PushSubscriptionDTO create(PushSubscriptionDTO pushSubscriptionDTO) {
        log.debug("Request to save PushSubscription : {}", pushSubscriptionDTO);
        PushSubscription pushSubscription = pushSubscriptionMapper.toEntity(pushSubscriptionDTO);
        final Optional<User> user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
        pushSubscription.setPerfil(user.get().getPerfilUsuario());
        pushSubscription = pushSubscriptionRepository.save(pushSubscription);
        return pushSubscriptionMapper.toDto(pushSubscription);
    }

    /**
     * Save a pushSubscription.
     *
     * @param pushSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PushSubscriptionDTO save(PushSubscriptionDTO pushSubscriptionDTO) {
        log.debug("Request to save PushSubscription : {}", pushSubscriptionDTO);
        PushSubscription pushSubscription = pushSubscriptionMapper.toEntity(pushSubscriptionDTO);
        pushSubscription = pushSubscriptionRepository.save(pushSubscription);
        return pushSubscriptionMapper.toDto(pushSubscription);
    }

    /**
     * Get all the pushSubscriptions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PushSubscriptionDTO> findAll() {
        log.debug("Request to get all PushSubscriptions");
        return pushSubscriptionRepository.findAll().stream()
            .map(pushSubscriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public PublicKey getUserPublicKey(PushSubscription sub) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        KeyFactory kf = KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        ECPoint point = ecSpec.getCurve().decodePoint(Base64.getDecoder().decode(sub.getKey()));
        ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);
        return kf.generatePublic(pubSpec);
    }

    public Notification sendPushMessage(PushSubscription sub, String title, String body, String icon) {
        Notification notification;
        PushService pushService;

        // Create a notification with the endpoint, userPublicKey from the subscription and a custom payload
        try {
            JSONObject jsonBase = new JSONObject();
            jsonBase.put("title", title);
            JSONObject jsonOptions = new JSONObject();
            jsonOptions.put("body", body);
            jsonOptions.put("icon", icon);
            jsonBase.put("options", jsonOptions);
            JSONObject jsonNotification = new JSONObject();
            jsonNotification.put("notification", jsonBase);
            notification = new Notification(
                sub.getEndpoint(),
                this.getUserPublicKey(sub),
                Base64.getDecoder().decode(sub.getAuth()),
                jsonNotification.toString().getBytes(UTF_8)
            );
            // Instantiate the push service, no need to use an API key for Push API
            pushService = new PushService();
            pushService.setPublicKey(Utils.loadPublicKey("BKzVYXkDTzcYQfur-mkNoSuP08tdwNqivtKmVMg1Nfdv8x8nP-SWdWyTClaXXf9QXN_MqszxrTB50mdWm_8WC4U"));
            pushService.setPrivateKey(Utils.loadPrivateKey("Yz-ewJCI36I4arboVKoJx7O_7q04zhCUxg1fZ_GcDPI"));
            // Send the notification
            pushService.send(notification);
            log.debug("Push Notification::" + jsonNotification.toJSONString());
            return notification;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (JoseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get one pushSubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PushSubscriptionDTO> findOne(Long id) {
        log.debug("Request to get PushSubscription : {}", id);
        return pushSubscriptionRepository.findById(id)
            .map(pushSubscriptionMapper::toDto);
    }

    /**
     * Delete the pushSubscription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PushSubscription : {}", id);
        pushSubscriptionRepository.deleteById(id);
    }
}
