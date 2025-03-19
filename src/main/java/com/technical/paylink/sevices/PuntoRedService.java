package com.technical.paylink.sevices;
import com.technical.paylink.models.Auth;
import com.technical.paylink.models.Supplier;
import com.technical.paylink.models.TransactionHistory;
import com.technical.paylink.respositories.TransactionHistoryRepository;
import com.technical.paylink.validators.requests.TransactionRequest;
import com.technical.paylink.validators.responses.AuthResponse;
import com.technical.paylink.validators.responses.TransactionResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PuntoRedService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final WebClient webClient;

    public PuntoRedService(TransactionHistoryRepository transactionHistoryRepository, WebClient webClient) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.webClient = webClient;
    }

    public Mono<AuthResponse> login(Auth auth){
        return webClient.post()
                .uri("/auth")
                .bodyValue(auth)
                .retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new WebClientResponseException(
                                        clientResponse.statusCode().value(), "Api Error", null, errorBody.getBytes(), null
                                )))
                )
                .bodyToMono(AuthResponse.class);
    }

    public Mono<List<Supplier>> getSupliers(String token) {
        return webClient.get()
                .uri("/getSuppliers")
                .header("authorization", token)
                .retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new WebClientResponseException(
                                        clientResponse.statusCode().value(), "Api Error", null, errorBody.getBytes(), null
                                )))
                )
                .bodyToMono(new ParameterizedTypeReference<List<Supplier>>() {});
    }

    public Mono<TransactionResponse> buyRecharge(String token, TransactionRequest transactionReq) {

        TransactionHistory transactionHistory = new TransactionHistory();

        transactionHistory.validateValue(transactionReq.getValue());
        transactionHistory.validateCellPhone(transactionReq.getCellPhone());

        return webClient.post()
                .uri("/buy")
                .header("authorization", token)
                .bodyValue(transactionReq)
                .retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new WebClientResponseException(
                                        clientResponse.statusCode().value(), "Api Error", null, errorBody.getBytes(), null
                                )))
                )
                .bodyToMono(TransactionResponse.class)
                .map(response -> {
                    TransactionHistory savedTransaction = this.saveTransaction(response, transactionReq.getSupplierId());
                    response.setId(savedTransaction.getId());
                    response.setSupplierId(savedTransaction.getSupplierId());
                    return response;
                });
    }

    TransactionHistory saveTransaction(TransactionResponse transaction, String supplierId) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionalID(transaction.getTransactionalID());
        transactionHistory.setValue(transaction.getValue());
        transactionHistory.setCellPhone(transaction.getCellPhone());
        transactionHistory.setMessage(transaction.getMessage());
        transactionHistory.setSupplierId(supplierId);

        return transactionHistoryRepository.save(transactionHistory);
    }
}
