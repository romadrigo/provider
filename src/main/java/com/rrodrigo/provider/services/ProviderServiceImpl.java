package com.rrodrigo.provider.services;

import com.rrodrigo.provider.model.Provider;
import com.rrodrigo.provider.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProviderServiceImpl implements ProviderService{

    @Autowired
    private ProviderRepository dao;

    @Override
    public Flux<Provider> getAll() {
        return dao.findAll();
    }

    @Override
    public Mono<Provider> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Provider> save(Provider producto) { return dao.save(producto);}
}
