package com.app.server.http;

import com.app.server.http.utils.APPResponse;
import com.app.server.http.utils.PATCH;
import com.app.server.services.RegimeProgramService;
import com.app.server.services.RegimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("regimeProgram")

public class RegimeProgramHttpService {

    private RegimeProgramService service;
    private ObjectWriter ow;

    public RegimeProgramHttpService() {
        service = RegimeProgramService.getInstance();
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
        return new APPResponse(service.getAll());
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getOne(@PathParam("id") String id) {

        return new APPResponse(service.getOne(id));
    }

    @GET
    @Path("/{id}/regime")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getRegime(@PathParam("id") String id) {

        return new APPResponse(service.getRegimeinRegimeProgram(id));
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse create(Object request) {
        return new APPResponse(service.create(request));
    }

    @PATCH
    @Path("/{id}/regime/{idtwo}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse createRegimeAsSub(@PathParam("id") String id,@PathParam("idtwo") String idtwo, Object request) {

        return new APPResponse(service.updateRegimeAsSubResource(id, idtwo, request));
    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("id") String id) {
        return new APPResponse(service.delete(id));
    }
}
