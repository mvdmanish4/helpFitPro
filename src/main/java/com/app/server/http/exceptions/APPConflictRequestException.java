package com.app.server.http.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


public class APPConflictRequestException extends WebApplicationException {

    public APPConflictRequestException(int errorCode, String errorMessage) {
        super(Response.status(Status.CONFLICT).entity(new APPExceptionInfo(
                Status.CONFLICT.getStatusCode(),
                Status.CONFLICT.getReasonPhrase(),
                errorCode,
                errorMessage)
        ).type("application/json").build());
    }

    public APPConflictRequestException(int errorCode) {
        super(Response.status(Status.BAD_REQUEST).entity(new APPExceptionInfo(
                Status.BAD_REQUEST.getStatusCode(),
                Status.BAD_REQUEST.getReasonPhrase(),
                errorCode)
        ).type("application/json").build());
    }

}