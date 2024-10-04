package com.rrodrigo.provider.services;

import com.rrodrigo.provider.model.Provider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProviderService {

    Flux<Provider> getAll();

    Mono<Provider> findById(String id);

    Mono<Provider> save(Provider producto);
}
