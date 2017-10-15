package hostmock.corba.clientresource;

import hostmock.corba.Host;
import hostmock.corba.HostHelper;
import hostmock.corba.HostClientHelper;
import hostmock.corba.HostConfiguration;
import org.omg.CORBA.StringHolder;

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

import javax.inject.Inject;
import javax.inject.Named;

@Path("/corba")
public class Root {
    @Inject
    private HostConfiguration configuration;

    private Host hostImpl = HostClientHelper.createHostImpl();

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{exname}")
    public Response ex(@PathParam("exname") String exName) {
        StringHolder ans = new StringHolder();
        this.hostImpl.sndAndRcv("sample", ans);
        System.out.println(ans.value);
        return Response.ok(ans.value).build();
    }
}
