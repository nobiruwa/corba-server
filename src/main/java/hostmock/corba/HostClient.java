package hostmock.corba;

import hostmock.PropertiesLoader;
import hostmock.SharedSingleton;
import hostmock.corba.HostClientHelper;
import hostmock.corba.HostConfiguration;

import java.util.Properties;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.io.IOException;

class HostClientServer {
    private URI baseUri = null;
    private HttpServer server = null;
    private HostConfiguration configuration;

    public HostClientServer(HostConfiguration configuration) {
        this.configuration = configuration;
    }
    public void start() {
        this.baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(8091).build();
        this.server = GrizzlyHttpServerFactory.createHttpServer(
            this.baseUri,
            ResourceConfig.forApplicationClass(RsResourceConfig.class)
            );
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
    }
    public void shutdown() {
        if (this.server != null) {
            this.server.shutdownNow();
        }
    }
    static class RsResourceConfig extends ResourceConfig {
        public RsResourceConfig() {
            // JacksonのJSON機能を有効にする
            // https://jersey.github.io/documentation/latest/media.html#json.jackson
            super(JacksonFeature.class);
            register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(SharedSingleton.getInstance().configuration.corba).to(HostConfiguration.class);
                }
            });
            packages("hostmock.corba.clientresource");
        }
    }
}

public class HostClient {
    private static final String PROPERTIES_FILE = "hostmock.properties";
    private static Properties loadProperties() {
        PropertiesLoader pLoader = new PropertiesLoader();
        return pLoader.load(PROPERTIES_FILE);
    }
    private static HostConfiguration loadConfiguration(Properties properties) {
        return new HostConfiguration(properties);
    }
    public static void main(String[] args) {
        HostConfiguration configuration = HostClient.loadConfiguration(HostClient.loadProperties());
        HostClientServer clientServer = new HostClientServer(configuration);
        clientServer.start();
    }
}
