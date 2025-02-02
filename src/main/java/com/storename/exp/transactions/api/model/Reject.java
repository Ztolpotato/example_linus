package com.storename.exp.transactions.api.model;


import com.storename.exp.transactions.sharedlib.Errors;

public sealed interface Reject {

    record BadRequest(Errors errors) implements Reject {
    }

    record Unauthorized() implements Reject {
    }

    record InternalServerError() implements Reject {
    }

    static Reject BAD_REQUEST(final Errors errors){ return new BadRequest(errors); }

    //This could map to a custom exception with information.
    default RuntimeException someException()
    {
        return switch (this) {
            case BadRequest badRequest ->
                new Http400("badrequest", "errorcode:09");
            default -> new Http500("Interal server error", "errorcode:20");
        };
    }

    static Reject fromDomain(final com.storename.exp.transactions.domain.result.Reject reject){
        return switch (reject){
            case com.storename.exp.transactions.domain.result.Reject.BadRequest badRequest ->
                    new BadRequest(new Errors("Bad request"));
            case com.storename.exp.transactions.domain.result.Reject.Unauthorized _ -> new Unauthorized();
            default -> new InternalServerError();
        };
    }


    class Http400 extends RuntimeException{
        private final String title;
        private final String error;

        public Http400(String title, String error){
            this.title = title;
            this.error = error;
        }
    }

    class Http500 extends RuntimeException{
        private final String title;
        private final String error;

        public Http500(String title, String error){
            this.title = title;
            this.error = error;
        }
    }


}
