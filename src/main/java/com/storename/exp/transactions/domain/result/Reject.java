package com.storename.exp.transactions.domain.result;

import org.springframework.validation.Errors;

public sealed interface Reject {
    record BadRequest(Errors errors) implements Reject {
    }

    record Unauthorized() implements Reject {
    }

    record InternalServerError() implements Reject {
    }
}
