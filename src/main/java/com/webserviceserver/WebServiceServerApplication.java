package com.webserviceserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    @GetMapping("/message")
    public ResponseEntity<WelcomeMessage> getMessage() {
        WelcomeMessage welcomeMessage = new WelcomeMessage("asd", LocalDateTime.now());
        return ResponseEntity.ok(welcomeMessage);
    }
}

@Data
@AllArgsConstructor
class WelcomeMessage {
    private String message;
    private LocalDateTime dateTime;
}
