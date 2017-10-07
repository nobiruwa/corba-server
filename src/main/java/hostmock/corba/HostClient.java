package hostmock.corba;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class HostClient {
    public static void main(String[] args) {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

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
            hostImpl.sndAndRcv("this is inq.", ans);
            System.out.println(ans.value);
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
