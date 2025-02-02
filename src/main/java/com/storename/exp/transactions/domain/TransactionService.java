package com.storename.exp.transactions.domain;

import com.storename.exp.transactions.domain.model.Transaction;
import com.storename.exp.transactions.domain.model.TransactionCommand;
import com.storename.exp.transactions.domain.result.Reject;
import com.storename.exp.transactions.infrastructure.TransactionRepository;
import io.vavr.control.Either;

import static java.util.Objects.requireNonNull;

public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = requireNonNull(repository);
    }

    public Either<Reject, Transaction> transferMoney(final TransactionCommand transactionCommand) {
        return notImplementedException;
    }
}
