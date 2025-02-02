package com.storename.exp.transactions.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import static java.util.Objects.requireNonNull;

public record Transaction(
        @Schema(description = "transaction id", example = "378651263", requiredMode = Schema.RequiredMode.REQUIRED)
        String id
) {

    public static TransactionInformation fromDomain(final com.storename.exp.transactions.domain.model.Transaction transaction){
        requireNonNull(transaction);
        return new TransactionInformation(transaction.id());
    }
}
