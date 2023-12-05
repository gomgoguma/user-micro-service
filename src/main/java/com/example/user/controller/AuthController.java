package com.example.user.controller;

import com.example.user.object.LoginReqObj;
import com.example.user.object.LoginResObj;
import com.example.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<LoginResObj> login(@RequestBody LoginReqObj loginReqObj) {
        return authService.login(loginReqObj);
    }
}
