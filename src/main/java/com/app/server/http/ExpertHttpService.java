package com.app.server.http;

import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPNotFoundException;
import com.app.server.http.utils.APPResponse;
import com.app.server.http.utils.PATCH;
import com.app.server.models.User.Expert;
import com.app.server.services.ExpertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("expert")

public class ExpertHttpService {
    private ExpertService expertService;
    private ObjectWriter ow;

    public ExpertHttpService() {
        expertService = ExpertService.getInstance();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @OPTIONS
    @PermitAll
    public Response optionsById() {
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse getOne(@PathParam("id") String id) {
        try {
            Expert d = expertService.getExpert(id);
            if (d == null)
                throw new APPNotFoundException(56,"Expert not found");
            return new APPResponse(d);
        }
        catch(IllegalArgumentException e){
            throw new APPNotFoundException(56,"Expert not found");
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

        return new APPResponse(expertService.update(id,request));

    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete(@PathParam("id") String id) {

        return new APPResponse(expertService.delete(id));
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON})
    public APPResponse delete() {

        return new APPResponse(expertService.deleteAll());
    }
}
