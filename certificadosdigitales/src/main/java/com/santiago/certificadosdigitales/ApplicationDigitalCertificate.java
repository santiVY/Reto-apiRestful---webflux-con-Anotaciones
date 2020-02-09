package com.santiago.certificadosdigitales;

import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.model.Certificate;
import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.repository.CertificateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class ApplicationDigitalCertificate {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationDigitalCertificate.class, args);
	}

	@Bean
	CommandLineRunner init(CertificateRepository repository){
		return args -> {

			Flux<Certificate> certificates = Flux.just(
					Certificate.builder().name("CertificadoCanalSVP").expirationDate(new Date()).area("SVP").nameKeystore("nameKeyStoreSVP").indicator("12").build(),
					Certificate.builder().name("CertificadoCanalAPP").expirationDate(new Date()).area("APP").nameKeystore("nameKeyStoreAPP").indicator("13").build()
			).flatMap(defaultCertificates-> repository.save(defaultCertificates));

			certificates.thenMany(repository.findAll())
					.subscribe(System.out::println);

		};
	}
}

