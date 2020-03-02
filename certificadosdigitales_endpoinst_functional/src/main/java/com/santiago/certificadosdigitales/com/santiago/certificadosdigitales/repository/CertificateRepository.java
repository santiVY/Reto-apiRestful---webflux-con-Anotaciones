package com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.repository;

import com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.model.Certificate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CertificateRepository extends ReactiveMongoRepository<Certificate, String> {
}
