package com.technical.paylink.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.paylink.models.Auth;
import com.technical.paylink.sevices.PuntoRedService;
import com.technical.paylink.validators.requests.TransactionRequest;
import com.technical.paylink.validators.responses.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public class PuntoRedApiController {

    @Autowired
    PuntoRedService puntoRedService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@Valid @RequestBody Auth auth) {
        return puntoRedService.login(auth)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ErrorResponse errorResponse;
                    try {
                        errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
                    }catch (Exception e) {
                        errorResponse = new ErrorResponse(ex.getStatusCode().value(), ex.getResponseBodyAsString());
                    }
                    return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(errorResponse.getMessage()));
                });
    }

    @GetMapping("/getSuppliers")
    public Mono<ResponseEntity<?>> getSuppliers(@RequestHeader("Authorization") String token) {
        return puntoRedService.getSupliers(token)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ErrorResponse errorResponse;
                    try {
                        errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
                    }catch (Exception e) {
                        errorResponse = new ErrorResponse(ex.getStatusCode().value(), ex.getResponseBodyAsString());
                    }
                    return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(errorResponse.getMessage()));
                });
    }


    @PostMapping("/buy")
    public Mono<ResponseEntity<?>> buyRecharge(@RequestHeader("Authorization") String token,
                                               @RequestBody TransactionRequest transaction) {
        return puntoRedService.buyRecharge(token, transaction)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ErrorResponse errorResponse;
                    try {
                        errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class);
                    } catch (Exception e) {
                        errorResponse = new ErrorResponse(ex.getStatusCode().value(), ex.getResponseBodyAsString());
                    }
                    return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(errorResponse.getMessage()));
                });
    }
}
