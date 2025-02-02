package com.storename.exp.transactions.api;

import com.storename.exp.transactions.api.model.Reject;
import com.storename.exp.transactions.api.model.Transaction;
import com.storename.exp.transactions.api.model.TransactionInformation;
import com.storename.exp.transactions.api.model.TransactionRequest;
import com.storename.exp.transactions.domain.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.Objects.requireNonNull;

@Validated
@RequestMapping(produces = "application/json")
@RestController
@Tag(name = "Transaction ")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(final TransactionService service){
        this.service = requireNonNull(service);
    }

    @Operation(summary = "Transfers money from Linus to Robin",
    description = "Linus should transfer 20 dollars everyday")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully transferred money", content = @Content(schema = @Schema(implementation = TransactionInformation.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = RuntimeException.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = RuntimeException.class)))
    })
    public ResponseEntity<TransactionInformation> transferMoney(
            @RequestHeader @Schema(description = "Customer's bank name") final String bankName,
            @RequestHeader @Schema(description = "Customer's user name") final String userName,
            @RequestHeader @Schema(description = "Customer's tier") final String tier,
            @RequestBody @Valid final TransactionRequest request
    ){
        return request.toDomain(bankName, userName, tier)
                .toEither()
                .mapLeft(Reject::BAD_REQUEST)
                .flatMap( transactionCommand -> service.transferMoney(transactionCommand)
                        .mapLeft(Reject::fromDomain))
                .map(Transaction::fromDomain)
                .map(response -> ResponseEntity
                        .created(URI.create("/some/url/v1/transaction/%s".formatted(response.id())))
                        .body(response))
                .getOrElseThrow(Reject::someException);
    }
}
