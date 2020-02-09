package com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.controller;

import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.model.Certificate;
import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/certificates")
@AllArgsConstructor
public class CertificateController {

    private CertificateRepository repository;

    /**
     * Controlador que devuelve todos los certificados
     * @return Flux<Certificate>
     */

    @GetMapping
    public Flux<Certificate> getAllCertificates(){
        return repository.findAll();
    }

    /**
     * Controlador que devuelve un certificado identificado por el id
     * @return Mono<ResponseEntity<Certificate>>
     */

    @GetMapping("{id}")
    public Mono<ResponseEntity<Certificate>> getCertificate(@PathVariable String id){
        return repository.findById(id).map(certificate -> ResponseEntity.ok(certificate))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Controlador que almacena un certificado enviado en el body
     * @param certificate
     * @return Mono<Certificate>
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Certificate> saveCertificate(@RequestBody Certificate certificate){
        return repository.save(certificate);
    }

    /**
     * Controlador que actualiza un certificado existente en base a su id y a la entidad enviada en el certificado
     * @param id
     * @param certificate
     * @return Mono<ResponseEntity<Certificate>>
     */

    @PutMapping("{id}")
    public Mono<ResponseEntity<Certificate>> updateCertificate(@PathVariable(value="id") String id, @RequestBody Certificate certificate){
        return repository.findById(id)
                .flatMap(foundCertificate -> {
                    foundCertificate.setName(certificate.getName());
                    foundCertificate.setExpirationDate(certificate.getExpirationDate());
                    foundCertificate.setArea(certificate.getArea());
                    foundCertificate.setNameKeystore(certificate.getNameKeystore());
                    foundCertificate.setIndicator(certificate.getIndicator());
                    return repository.save(foundCertificate);
                }).map(updateCertificate -> ResponseEntity.ok(updateCertificate))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    /**
     * Controlador que elimina un certificado en base a su id
     * @param id
     * @return <ResponseEntity<Void>>
     */

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteCertificate(@PathVariable(value = "id") String id){
        return repository.findById(id)
                .flatMap(foundCertificate -> repository.delete(foundCertificate)
                        .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Controlador que elimina todos los certificados
     * @return Mono<Void>
     */

    @DeleteMapping
    public Mono<Void> deleteAllCertificate(){
        return repository.deleteAll();
    }




}
