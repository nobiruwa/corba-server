package hostmock.service.resource;

import hostmock.CacheMap;
import hostmock.TelegramFinder;
import hostmock.service.ExSender;
import hostmock.service.ServiceConfiguration;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import javax.inject.Inject;
import javax.inject.Named;

@Path("/ans")
public class Ans {
    @Inject
    private ServiceConfiguration configuration;
    @Inject
    @Named("cachedAns")
    private CacheMap cachedAns;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{ansname}")
    public Response put(@PathParam("ansname") String ansName, AnsWithBodyRequest request) {
        String ansBody = request.body;
        this.cachedAns.put(ansName, ansBody);
        return Response.ok(ansName + " in " + "cache").build();
    }
    @DELETE
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{ansname}")
    public Response delete(@PathParam("ansname") String ansName) {
        this.cachedAns.remove(ansName);
        return Response.ok(ansName + " in " + "cache").build();
    }
}
