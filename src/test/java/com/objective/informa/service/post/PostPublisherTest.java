package com.objective.informa.service.post;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.PerfilUsuario;
import com.objective.informa.domain.Post;
import com.objective.informa.domain.PushSubscription;
import com.objective.informa.domain.User;
import com.objective.informa.repository.UserRepository;
import com.objective.informa.service.PushSubscriptionService;

@RunWith(MockitoJUnitRunner.class)
public class PostPublisherTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PushSubscriptionService pushSubscriptionService;



    private static User criaUser(String firstName, String lastName, String... subs) {
        User usuario = new User();
        usuario.setFirstName(firstName);
        usuario.setLastName(lastName);
        PerfilUsuario perfilRecebedor = new PerfilUsuario();
        perfilRecebedor.setUsuario(usuario);
        usuario.setPerfilUsuario(perfilRecebedor);
        Arrays.asList(subs).forEach( sub ->
            perfilRecebedor.addSubscriptions(new PushSubscription().auth(sub))
        );
        return usuario;
    }

    @Test
    public void testPublisher() throws InterruptedException, ExecutionException, TimeoutException {
        final PostPublisher postPublisher = new PostPublisher(userRepository, pushSubscriptionService);
        User autor = criaUser("Autor", "Souza", "authAutor");
        User recebedor = criaUser("Recebedor", "Silva", "authRecebedor1", "authRecebedor2");
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(autor, recebedor));
        Grupo grupo = new Grupo();
        grupo.setNome("grupo-teste");
        Post post = new Post();
        post.setAutor(autor);
        post.setGrupo(grupo);
        postPublisher.publish(post);
        System.out.println("teste");
    }
//
//    public static void main(String args[])  throws InterruptedException, ExecutionException, TimeoutException {
//        final ActorSystem system = ActorSystem.create("QuickStart");
//        int bufferSize = 1000;
//        int elementsToProcess = 5;
//        System.out.println(0);
//        SourceQueueWithComplete<Integer> sourceQueue =
//            Source.<Integer>queue(bufferSize, OverflowStrategy.backpressure())
//                .map(x -> x * x)
//                .to(Sink.foreach(x -> System.out.println("got: " + x)))
//                .run(system);
//        for (int i = 1; i < 1000; i++) {
//            sourceQueue.offer(i);
//        }
//        System.out.println(1);
//    }
}
