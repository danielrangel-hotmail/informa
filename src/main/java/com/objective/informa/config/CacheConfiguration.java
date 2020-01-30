package com.objective.informa.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.objective.informa.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.objective.informa.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.objective.informa.domain.User.class.getName());
            createCache(cm, com.objective.informa.domain.Authority.class.getName());
            createCache(cm, com.objective.informa.domain.User.class.getName() + ".authorities");
            createCache(cm, com.objective.informa.domain.Grupo.class.getName());
            createCache(cm, com.objective.informa.domain.Post.class.getName());
            createCache(cm, com.objective.informa.domain.Mensagem.class.getName());
            createCache(cm, com.objective.informa.domain.Post.class.getName() + ".arquivos");
            createCache(cm, com.objective.informa.domain.Mensagem.class.getName() + ".arquivos");
            createCache(cm, com.objective.informa.domain.Arquivo.class.getName());
            createCache(cm, com.objective.informa.domain.Post.class.getName() + ".linksExternos");
            createCache(cm, com.objective.informa.domain.Mensagem.class.getName() + ".linksExternos");
            createCache(cm, com.objective.informa.domain.LinkExterno.class.getName());
            createCache(cm, com.objective.informa.domain.PushSubscription.class.getName());
            createCache(cm, com.objective.informa.domain.PerfilUsuario.class.getName());
            createCache(cm, com.objective.informa.domain.PerfilUsuario.class.getName() + ".subscriptions");
            createCache(cm, com.objective.informa.domain.Grupo.class.getName() + ".usuarios");
            createCache(cm, com.objective.informa.domain.PerfilUsuario.class.getName() + ".grupos");
            createCache(cm, com.objective.informa.domain.PerfilGrupo.class.getName());
            createCache(cm, com.objective.informa.domain.Grupo.class.getName() + ".topicos");
            createCache(cm, com.objective.informa.domain.Topico.class.getName());
            createCache(cm, com.objective.informa.domain.Topico.class.getName() + ".substituidos");
            createCache(cm, com.objective.informa.domain.Topico.class.getName() + ".grupos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
