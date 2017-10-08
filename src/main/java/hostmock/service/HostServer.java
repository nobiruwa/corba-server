package hostmock.service;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.io.IOException;

public class HostServer {
    private URI baseUri = null;
    private HttpServer server = null;

    public void start(int port) {
        this.baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(port).build();
        this.server = GrizzlyHttpServerFactory.createHttpServer(
                this.baseUri,
                ResourceConfig.forApplicationClass(RsResourceConfig.class)
            );
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
    }

    public void shutdown() {
        if (this.server != null) {
            this.server.stop();
        }
    }

    public static class RsResourceConfig extends ResourceConfig {
        public RsResourceConfig() {
            packages("hostmock.service.resource");
        }
    }

    public static void main(String[] args) {
        HostServer server = new HostServer();
        server.start(8090);
    }
}
