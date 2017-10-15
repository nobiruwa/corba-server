package hostmock.corba;

import hostmock.SharedSingleton;
import hostmock.corba.Host;
import hostmock.corba.HostHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class HostClientHelper {
    public static Host createHostImpl() {
        return (new HostClientHelper()).createHostImpl(SharedSingleton.getInstance().configuration.corba.orbInitialPort);
    }
    public Host createHostImpl(int orbInitialPort) {
        try {
            String[] orbArgs = new String[] {
                "-ORBInitialPort",
                String.valueOf(orbInitialPort)
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
            return hostImpl;
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
            return null;
        }
    }
}
