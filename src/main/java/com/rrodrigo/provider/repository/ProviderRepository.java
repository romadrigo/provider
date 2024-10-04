package com.rrodrigo.provider.repository;

import com.rrodrigo.provider.model.Provider;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProviderRepository extends ReactiveMongoRepository<Provider, String> {
}
