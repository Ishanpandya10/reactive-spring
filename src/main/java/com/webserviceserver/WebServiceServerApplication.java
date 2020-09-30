package com.webserviceserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class WebServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceServerApplication.class, args);
    }
}

/**
 * Creating an Http end point
 */
@RestController
class Api {
    @GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<WelcomeMessage> getMessage() {
        List<WelcomeMessage> welcomeMessages = Arrays.asList(new WelcomeMessage("asd", LocalDateTime.now())) ;
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
