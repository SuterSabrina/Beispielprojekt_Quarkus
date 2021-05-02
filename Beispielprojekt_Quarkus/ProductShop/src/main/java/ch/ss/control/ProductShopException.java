package ch.ss.control;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ProductShopException extends WebApplicationException {

    public ProductShopException(String message, Response.Status status) {
        super(Response
                .status(status)
                .header("Reason", message)
                .build());
    }
}
