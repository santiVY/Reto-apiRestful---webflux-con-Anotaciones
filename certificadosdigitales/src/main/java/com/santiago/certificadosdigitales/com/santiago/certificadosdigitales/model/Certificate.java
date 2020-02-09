package com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Certificate {

    @Id
    private String id;

    private String name;
    private Date expirationDate;
    private String area;
    private String nameKeystore;
    private String indicator;
}
