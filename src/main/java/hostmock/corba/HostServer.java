package hostmock.corba;

import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NameComponent;

class HostImpl extends HostPOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        this.orb = orb_val;
    }

    @Override
    public void sndAndRcv(String inq, StringHolder ans) {
        ans.value = this.createAns(this.parseInq(inq));
    }

    private String parseInq(String inq) {
        return inq.toLowerCase();
    }

    private String createAns(String parsedInq) {
        return parsedInq + "ANS日本語";
    }
}

public class HostServer {
    public static void main(String[] args) {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            HostImpl hostImpl = new HostImpl();
            hostImpl.setORB(orb);

            // create a tie, with servant being the delegate.
            HostPOATie tie = new HostPOATie(hostImpl, rootpoa);

            // obtain the objectRef for the tie
            // this step also implicitly activates the object
            Host href = tie._this(orb);

            // get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            String name = "Host";
            NameComponent path[] = ncRef.to_name( name );
            ncRef.rebind(path, href);

            System.out.println("HostServer ready and waiting ...");

            // wait for invocations from clients
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
        System.out.println("HostServer Exiting ...");
    }
}
