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

@Path("/ex")
public class Ex {
    private Response _ex(String exName) {
        ServiceConfiguration config = SharedSingleton.getInstance().configuration.service;
        String exAddress = config.exAddress;
        int exPort = config.exPort;
        int exPath = config.exPath;
        String exDir = config.exDir;
        ExSender exSender = new ExSender(exAddress, exPort, exPath);
        ConcurrentHashMap<String, String> cachedEx = SharedSingleton.getInstance().cachedEx;
        if (cachedEx.containsKey(exName)) {
            String content = cachedEx.get(exName);
            exSender.send(content);
            return Response.ok(exName + " in " + "cache").build();
        }
        TelegramFinder exFinder = new TelegramFinder(exDir);
        if (exFinder.exists(exName)) {
            try {
                String content = exFinder.read(exName);
                exSender.send(content);
                return Response.ok(exName + " in " + exDir).build();
            } catch (IOException e) {
                return Response.status(500).build();
            }
        } else {
            return Response.status(404).build();
        }
    }
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{exname}")
    public Response ex(@PathParam("exname") String exName) {
        return this._ex(exName);
    }
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{exname}")
    public Response put(@PathParam("exname") String exName, ExWithBodyRequest request) {
        String exBody = request.body;
        SharedSingleton.getInstance().cachedEx.put(exName, exBody);
        return Response.ok(exName + " in " + "cache").build();
    }
    @DELETE
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{exname}")
    public Response delete(@PathParam("exname") String exName) {
        SharedSingleton.getInstance().cachedEx.remove(exName);
        return Response.ok(exName + " in " + "cache").build();
    }
}
