package com.storename.exp.transactions.api.model;

import com.storename.exp.transactions.domain.model.TransactionCommand;
import com.storename.exp.transactions.sharedlib.Errors;
import io.vavr.control.Validation;
import org.apache.commons.lang3.NotImplementedException;

public class TransactionRequest {
    public Validation<Errors, TransactionCommand> toDomain(String bankName, String userName, String tier) {
        return NotImplementedException;
    }
}
