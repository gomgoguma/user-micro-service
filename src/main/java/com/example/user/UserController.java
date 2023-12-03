package com.example.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping
    public Mono<String> user() {
        log.info("user service");
        return Mono.just("user micro service");
    }
}
