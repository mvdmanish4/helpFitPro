package com.app.server.http;

import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPNotFoundException;
import com.app.server.http.utils.APPResponse;
import com.app.server.http.utils.PATCH;
import com.app.server.models.User.User;
import com.app.server.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")

public class UserHttpService {
    private UserService service;
    private ObjectWriter ow;


    public UserHttpService() {
        service = UserService.getInstance();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    @OPTIONS
    @PermitAll
    public Response optionsById() {
        return Response.ok().build();
    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getAll() {

        return new APPResponse(service.getAllUsers());
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getOne(@PathParam("id") String id) {
        try {
            User d = service.getUser(id);
            if (d == null)
                throw new APPNotFoundException(56,"User not found");
            return new APPResponse(d);
        }
        catch(IllegalArgumentException e){
            throw new APPNotFoundException(56,"User not found");
        }
        catch (Exception e) {
            throw new APPInternalServerException(0,"Something went wrong.");
        }
    }

    @PATCH
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public APPResponse update(@PathParam("id") String id, Object request){
        return new APPResponse(service.update(id,request));
    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("id") String id) {
        return new APPResponse(service.delete(id));
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete() {
        return new APPResponse(service.deleteAll());
    }

    @POST
    @Path("{id}/transactions")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse createTranscation(@Context HttpHeaders headers, @PathParam("id") String id, Object request) {
        return new APPResponse(service.createTranscation(headers, id, request));
    }

    @GET
    @Path("{id}/transactions")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getUserTransactions(@Context HttpHeaders headers,@PathParam("id") String id) {
        return new APPResponse(service.getUserTransactions(headers, id));
    }

    @GET
    @Path("{id}/regimeProgram")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getUserRegimeProgram(@Context HttpHeaders headers,@PathParam("id") String id) {
        return new APPResponse(service.getUserRegimeProgram(headers, id));
    }

    @POST
    @Path("{id}/regimeProgram")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse createUserRegimeProgram(@Context HttpHeaders headers, @PathParam("id") String id, Object request) {
        return new APPResponse(service.createUserRegimeProgram(headers, id, request));
    }

    @DELETE
    @Path("{id}/regimeProgram")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@Context HttpHeaders headers, @PathParam("id") String id) {
        return new APPResponse(service.deleteUserRegimeProgram(headers, id));
    }

}
