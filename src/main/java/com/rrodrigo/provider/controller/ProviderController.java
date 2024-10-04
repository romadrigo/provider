package com.rrodrigo.provider.controller;

import com.rrodrigo.provider.ProviderApplication;
import com.rrodrigo.provider.api.ProvidersApi;
import com.rrodrigo.provider.model.Provider;
import com.rrodrigo.provider.repository.ProviderRepository;
import com.rrodrigo.provider.services.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController implements ProvidersApi {

    private static final Logger log = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private ProviderService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Provider>>> getAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Provider>> getOne(@PathVariable String id){
        return service.findById(id).map(p -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Provider>> create(@RequestBody Provider provider){
        if(provider.getDate() == null)
            provider.setDate(new Date());

        return service.save(provider).map(p -> ResponseEntity
                .created(URI.create("/api/v1/provider".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p));
    }

}
