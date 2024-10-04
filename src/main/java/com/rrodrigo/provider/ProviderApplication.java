package com.rrodrigo.provider;

import com.rrodrigo.provider.model.Provider;
import com.rrodrigo.provider.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class ProviderApplication implements CommandLineRunner {

	@Autowired
	ProviderRepository providerRepository;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(ProviderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			mongoTemplate.dropCollection("provider").subscribe();

			Flux.just(new Provider("Tokio","Direccion 1","+57985285", true),
					new Provider("Moscu","Direccion 21","+47852662", false),
				new Provider("Rio","Direccion 12","+58922885", true))
				.flatMap(provider -> {
					provider.setDate(new Date());
					return providerRepository.save(provider);
				})
				.subscribe(provider -> log.info("Insertando provider " + provider.getId()));
	}
}
