package hostmock.corba;

import hostmock.PropertiesLoader;
import hostmock.SharedSingleton;
import hostmock.corba.HostConfiguration;

import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.io.IOException;

class HostClientSingleton {
    private Host hostImpl;

    private static HostClientSingleton instance;
    private HostClientSingleton() {
    }
    public void setHost(Host hostImple) {
        this.hostImpl = hostImpl;
    }
    public Host getHost() {
        return this.hostImpl;
    }
    public static synchronized HostClientSingleton getInstance() {
        if(instance == null) {
            instance = new HostClientSingleton();
        }
        return instance;
    }
}

class HostClientServer {
    private URI baseUri = null;
    private HttpServer server = null;
    private Host hostImpl;
    private HostConfiguration configuration;

    public HostClientServer(HostConfiguration configuration, Host hostImpl) {
        this.configuration = configuration;
        this.hostImpl = hostImpl;
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
                    // bind(HostClientSingleton.getInstance().getHost()).to(Host.class);
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
        try {
            String[] orbArgs = new String[]{
                "-ORBInitialPort",
                String.valueOf(configuration.orbInitialPort)
            };
            // create and initialize the ORB
            ORB orb = ORB.init(orbArgs, null);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            String name = "Host";
            Host hostImpl = HostHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Obtained a handle on server object: " + hostImpl);
            HostClientSingleton.getInstance().setHost(hostImpl);
            HostClientServer clientServer = new HostClientServer(configuration, hostImpl);
            clientServer.start();
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
