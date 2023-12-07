package com.example.user.repository;

import com.example.user.entity.Auth;
import reactor.core.publisher.Mono;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthRepository extends ReactiveCrudRepository<Auth, Long> {
    Mono<Auth> findByUsername(String username);
}
