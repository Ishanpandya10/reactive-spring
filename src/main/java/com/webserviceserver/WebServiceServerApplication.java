package com.webserviceserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class WebServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceServerApplication.class, args);
    }

    //Creating endpoint in reactive style
    @Bean
    RouterFunction<ServerResponse> http() {
        return route()
                .GET("/message1", serverRequest -> {
                    List<WelcomeMessage> welcomeMessages = Arrays.asList(new WelcomeMessage("Reactive style webservice", LocalDateTime.now()));
                    Flux<WelcomeMessage> welcomeMessageFlux = Flux.fromStream(welcomeMessages.stream())
                            .delayElements(Duration.ofSeconds(1));
                    return ServerResponse.ok().body(welcomeMessageFlux, WelcomeMessage.class);
                })
                .build();
    }
}

/**
 * Creating an Http end point
 */
@RestController
class Api {
    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<WelcomeMessage> getMessage() {
        List<WelcomeMessage> welcomeMessages = Arrays.asList(new WelcomeMessage("Traditional style of building rest", LocalDateTime.now()));
        return Flux.fromStream(welcomeMessages.stream())
                .limitRate(1)
                .delayElements(Duration.ofSeconds(1));
    }
}

@Data
@AllArgsConstructor
class WelcomeMessage {
    private String message;
    private LocalDateTime dateTime;
}
