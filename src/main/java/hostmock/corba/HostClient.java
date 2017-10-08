package hostmock.corba;

import hostmock.PropertiesLoader;

import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

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
            StringHolder ans = new StringHolder();
            hostImpl.sndAndRcv("sample", ans);
            System.out.println(ans.value);
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
