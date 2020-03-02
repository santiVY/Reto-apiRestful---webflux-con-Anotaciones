package com.santiago.certificadosdigitales.com.santiago.certificadosdigitales.controller;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Route {

    private static final  String ROUTE_DIGITAL_CERTIFICATE = "/certificates";
    private static final  String ROUTE_DIGITAL_CERTIFICATE_BY_ID = "/certificates/{id}";

    @Bean
    RouterFunction<ServerResponse> routes(Handler handler){
        return route(GET(ROUTE_DIGITAL_CERTIFICATE).and(accept(MediaType.APPLICATION_JSON)), handler::getAllCertificates)
                .andRoute(GET(ROUTE_DIGITAL_CERTIFICATE_BY_ID).and(accept(MediaType.APPLICATION_JSON)), handler::getCertificate)
                .andRoute(POST(ROUTE_DIGITAL_CERTIFICATE).and(accept(MediaType.APPLICATION_JSON)), handler::saveCertificate)
                .andRoute(PUT(ROUTE_DIGITAL_CERTIFICATE_BY_ID).and(accept(MediaType.APPLICATION_JSON)), handler::updateCertificate)
                .andRoute(DELETE(ROUTE_DIGITAL_CERTIFICATE_BY_ID).and(accept(MediaType.APPLICATION_JSON)), handler::deleteCertification)
                .andRoute(DELETE(ROUTE_DIGITAL_CERTIFICATE).and(accept(MediaType.APPLICATION_JSON)), handler::deleteAllCertificates);
    }

}
