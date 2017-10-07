package hostmock.service;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.io.IOException;

public class HostServer {
    static final URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(8090).build();
    public static void main(String[] args) {
        try {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
BASE_URI,
ResourceConfig.forApplicationClass(RsResourceConfig.class)
);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }

    public static class RsResourceConfig extends ResourceConfig {
        public RsResourceConfig() {
            packages("hostmock.service.resource");
        }
    }

}
