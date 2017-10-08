package hostmock.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class Root {
    @GET
    public Response hello() {
        return Response.ok("Hello jersey-grizzly!").build();
    }
}
