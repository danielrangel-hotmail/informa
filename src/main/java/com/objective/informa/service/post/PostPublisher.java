package com.objective.informa.service.post;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.Post;
import com.objective.informa.domain.User;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.PushSubscriptionService;

import akka.actor.ActorSystem;
import akka.stream.OverflowStrategy;
import akka.stream.QueueOfferResult;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.javadsl.SourceQueueWithComplete;
import nl.martijndwars.webpush.Notification;

@Service
@Transactional
public class PostPublisher {

    private final Logger log = LoggerFactory.getLogger(PostPublisher.class);

    private static int BUFFER_SIZE = 1000;

    private final ActorSystem actorSystem;
    private final UserRepository userRepository;
    private final PushSubscriptionService pushSubscriptionService;
    private final SourceQueueWithComplete<Post> postQueue;

    public PostPublisher(UserRepository userRepository,
        PushSubscriptionService pushSubscriptionService) {
        this.userRepository = userRepository;
        this.pushSubscriptionService = pushSubscriptionService;
        this.actorSystem = ActorSystem.create("PostPublisher");
        this.postQueue = postsParaPublicar();
    }

    public SourceQueueWithComplete<Post> getPostQueue() {
        return postQueue;
    }

    public CompletionStage<QueueOfferResult> publish(Post post) {
        final List<User> all = this.userRepository.findAll();
        all.forEach(u-> temSubscriptions(u));
        return this.postQueue.offer(post);
    }

    private SourceQueueWithComplete<Post> postsParaPublicar() {
        return Source.<Post>queue(BUFFER_SIZE, OverflowStrategy.backpressure())
            .mapConcat(this::criaPostPublisherUser)
            .filter(this::eParaPostar)
            .to(Sink.foreach(this::publica))
            .run(actorSystem);
    }

    private boolean eParaPostar(PostPublisherUser postPublisherUser) {
        return true;
    }

    private boolean temSubscriptions(User user) {
        log.debug("user:", user);
        log.debug("perfil:", user.getPerfilUsuario());
        if (user.getPerfilUsuario() == null) return false;
        log.debug("subscriptions:", user.getPerfilUsuario().getSubscriptions());
        if (user.getPerfilUsuario().getSubscriptions() == null) return false;
        final boolean empty = user.getPerfilUsuario().getSubscriptions().isEmpty();
        return !empty;
    }

    private List<PostPublisherUser> criaPostPublisherUser(Post post) {
        final List<User> all = this.userRepository.findAll();
        final List<PostPublisherUser> collect = all
            .stream()
            .filter(user -> temSubscriptions(user))
            .filter(user -> user != post.getAutor())
            .map(user -> new PostPublisherUser(post, user))
            .collect(Collectors.toList());
        log.debug("Cria Post Publisher", collect);
        return collect;
    }

    private List<Notification> publica(PostPublisherUser postPublisherUser) {
        final User autor = postPublisherUser.post.getAutor();
        String title = "Novo post de " + autor.getFirstName() + " " + autor.getLastName();
        String body = "Grupo: " + postPublisherUser.post.getGrupo().getNome();
        String icon = "/content/images/logo-informa.png";
        return postPublisherUser.user.getPerfilUsuario().getSubscriptions()
            .stream()
            .map(sub -> this.pushSubscriptionService.sendPushMessage(sub, title, body, icon))
            .collect(Collectors.toList());
    }

    private class PostPublisherUser {
        public Post post;
        public User user;
        public PostPublisherUser(Post post, User user) {
            this.post = post;
            this.user = user;
        }

        @Override
        public String toString() {
            return "PostPublisherUser{" +
                "post=" + post.toString() +
                ", user=" + user.toString() +
                '}';
        }
    }
}


/// post => post + subscriptions por user => (filtra por grupo) => faz os posts para user
