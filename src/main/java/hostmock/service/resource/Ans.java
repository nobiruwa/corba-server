package hostmock.service.resource;

import hostmock.TelegramFinder;
import hostmock.service.ExSender;
import hostmock.service.ServiceConfiguration;

import hostmock.SharedSingleton;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
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

@Path("/ans")
public class Ans {
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{ansname}")
    public Response put(@PathParam("ansname") String ansName, AnsWithBodyRequest request) {
        String ansBody = request.body;
        SharedSingleton.getInstance().cachedAns.put(ansName, ansBody);
        return Response.ok(ansName + " in " + "cache").build();
    }
    @DELETE
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{ansname}")
    public Response delete(@PathParam("ansname") String ansName) {
        SharedSingleton.getInstance().cachedAns.remove(ansName);
        return Response.ok(ansName + " in " + "cache").build();
    }
}
