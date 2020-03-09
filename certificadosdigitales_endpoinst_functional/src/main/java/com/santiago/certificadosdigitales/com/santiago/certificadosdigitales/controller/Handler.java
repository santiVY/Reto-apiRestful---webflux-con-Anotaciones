package com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.controller;

import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.model.Certificate;
import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Component
public class Handler {

    CertificateRepository repository;


    public Mono<ServerResponse> getAllCertificates(ServerRequest request){
        Flux<Certificate> certificates = repository.findAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(certificates, Certificate.class);
    }


    public Mono<ServerResponse> getCertificate(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Certificate> certificate = repository.findById(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(certificate, Certificate.class);
    }


    public Mono<ServerResponse> saveCertificate(ServerRequest request){
        Mono<Certificate> certificateMono = request.bodyToMono(Certificate.class);
        return certificateMono.flatMap(certificate->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(repository.save(certificate),Certificate.class));

    }


    public Mono<ServerResponse> updateCertificate(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Certificate> findCertificateMono = repository.findById(id);
        Mono<Certificate> certificateMono = request.bodyToMono(Certificate.class);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return  certificateMono.zipWith(findCertificateMono,
                (certificate, existingCertificate)-> Certificate.builder()
                        .id(existingCertificate.getId())
                        .name(certificate.getName())
                        .expirationDate(certificate.getExpirationDate())
                        .area(certificate.getArea())
                        .nameKeystore(certificate.getNameKeystore())
                        .indicator(certificate.getIndicator())
                        .build()).flatMap(product -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.save(product), Certificate.class)).switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> deleteCertification(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Void> result =  repository.deleteById(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, String.class);
    }


    public Mono<ServerResponse> deleteAllCertificates(ServerRequest request){
        Mono<Void> result =  repository.deleteAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, String.class);
    }
}
