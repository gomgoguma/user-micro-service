package com.example.user.service;

import com.example.user.object.LoginReqObj;
import com.example.user.object.LoginResObj;
import com.example.user.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository accountRepository;

    public Mono<LoginResObj> login(LoginReqObj loginReqObj) {
        return accountRepository.findByUsernameAndPassword(loginReqObj.getUsername(), loginReqObj.getPassword())
                .map(auth -> {
                    LoginResObj obj = new LoginResObj();
                    obj.setResMsg("로그인 성공");
                    obj.setAccessToken("");
                    return obj;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    LoginResObj obj = new LoginResObj();
                    obj.setResMsg("로그인 실패");
                    return Mono.just(obj);
                }));
    }
}
