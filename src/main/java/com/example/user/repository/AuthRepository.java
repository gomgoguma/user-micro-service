package com.example.user.repository;

import com.example.user.entity.Auth;
//import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthRepository extends ReactiveCrudRepository<Auth, Long> {
    Mono<Auth> findByUsernameAndPassword(String username, String password);
}
