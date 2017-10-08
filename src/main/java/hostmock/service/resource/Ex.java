package hostmock.service.resource;

import hostmock.TelegramFinder;
import hostmock.service.ExSender;

import hostmock.SharedSingleton;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

@Path("/ex")
public class Ex {
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response ex(@FormParam("exname") String exName,
                       @FormParam("exaddress") String exAddress,
                       @FormParam("export") int exPort,
                       @FormParam("expath") int exPath) {
        ExSender exSender = new ExSender(exAddress, exPort, exPath);
        String exDir = SharedSingleton.getInstance().configuration.service.exDir;
        ConcurrentHashMap<String, String> cachedAns = SharedSingleton.getInstance().cachedAns;
        if (cachedAns.containsKey(exName)) {
            String content = cachedAns.get(exName);
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
}
