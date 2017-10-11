package hostmock.service;

import hostmock.SharedSingleton;
import hostmock.service.ServiceConfiguration;
import hostmock.CacheMap;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.io.IOException;

public class ServiceServer {
    private URI baseUri = null;
    private HttpServer server = null;
    private ServiceConfiguration configuration = null;
    private CacheMap cachedEx;
    public ServiceServer(ServiceConfiguration configuration, CacheMap cachedEx) {
        this.configuration = configuration;
        this.cachedEx = cachedEx;
    }
    public void start() {
        this.baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(configuration.port).build();
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

    public static class RsResourceConfig extends ResourceConfig {
        public RsResourceConfig() {
            // JacksonのJSON機能を有効にする
            // https://jersey.github.io/documentation/latest/media.html#json.jackson
            super(JacksonFeature.class);
            register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(SharedSingleton.getInstance().configuration.service).to(ServiceConfiguration.class);
                    // bind(SharedSingleton.getInstance().cachedAns).named("cachedAns").to(ConcurrentHashMap.class);
                    // bind(SharedSingleton.getInstance().cachedEx).to(ConcurrentHashMap.class);
                    bind(SharedSingleton.getInstance().cachedAns).to(CacheMap.class).named("cachedAns");
                    bind(SharedSingleton.getInstance().cachedEx).to(CacheMap.class).named("cachedEx");
                }
            });
            packages("hostmock.service.resource");
        }
    }
}
