package com.example.user.service;

import com.example.user.config.JwtConfig;
import com.example.user.entity.Auth;
import com.example.user.entity.User;
import com.example.user.object.LoginReqObj;
import com.example.user.object.LoginResObj;
import com.example.user.object.SignUpReqObj;
import com.example.user.object.SignUpResObj;
import com.example.user.repository.AuthRepository;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtConfig jwtConfig;

    public Mono<LoginResObj> login(LoginReqObj loginReqObj, ServerHttpResponse response) {
        return authRepository.findByUsername(loginReqObj.getUsername())
                .map(auth -> {
                    LoginResObj obj = new LoginResObj();
                    if (encoder.matches(loginReqObj.getPassword(), auth.getPassword())) { // matches(입력 PW, 암호화된 PW)
                        obj.setResMsg("로그인 성공");
                        obj.setAccessToken(jwtConfig.createAccessToken(auth.getUsername()));
                        ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtConfig.createRefreshToken(auth.getUsername()))
                                .maxAge(60*60*24*7) // 쿠키 유효 시간 (초)
                                .domain("localhost")
                                .path("/")    // 쿠키 경로
                                .build();
                        response.addCookie(cookie);
                    }
                    else {
                        obj.setResMsg("로그인 실패");
                    }
                    return obj;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    LoginResObj obj = new LoginResObj();
                    obj.setResMsg("로그인 실패");
                    return Mono.just(obj);
                }));
    }

    @Transactional
    public Mono<SignUpResObj> signUp(SignUpReqObj signUpReqObj) {
        signUpReqObj.setPassword(encoder.encode(signUpReqObj.getPassword()));
        User user = new User();
        user.setName(signUpReqObj.getName());
        user.setEmail(signUpReqObj.getEmail());
        user.setRole(signUpReqObj.getRole());
        return userRepository.save(user)
            .flatMap(saveUser -> {
                Auth auth = new Auth();
                auth.setUserId(saveUser.getId());
                auth.setUsername(signUpReqObj.getUsername());
                auth.setPassword(signUpReqObj.getPassword());
                return authRepository.save(auth);
            })
            .map(saveAuth -> {
                SignUpResObj response = new SignUpResObj();
                response.setResMsg("회원가입 성공");
                return response;
            });

    }
}
