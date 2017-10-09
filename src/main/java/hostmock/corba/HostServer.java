package hostmock.corba;

import hostmock.IORWriter;
import hostmock.TelegramFinder;
import hostmock.SharedSingleton;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NameComponent;
import org.omg.CORBA.UserException;

class HostImpl extends HostPOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        this.orb = orb_val;
    }

    @Override
    public void sndAndRcv(String inq, StringHolder ans) {
        String ansDir = SharedSingleton.getInstance().configuration.corba.ansDir;
        TelegramFinder ansFinder = new TelegramFinder(ansDir);
        ConcurrentHashMap<String, String> cachedEx = SharedSingleton.getInstance().cachedAns;
        if (cachedEx.containsKey(inq)) {
            String content = cachedEx.get(inq);
            ans.value = content;
            return;
        }
        try {
            if (ansFinder.exists(inq)) {
                String content = ansFinder.read(inq);
                ans.value = content;
                return;
            }
            ans.value = "could not find an ans telegram '"+ inq +"' from " + ansDir;
            return;
        } catch (IOException e) {
            ans.value = "could not load an ans telegram.";
            return;
        }
    }
}

public class HostServer {
    private HostConfiguration configuration;
    public HostServer(HostConfiguration configuration) {
        this.configuration = configuration;
    }
    private Boolean isWindows() {
        return System.getProperty("os.name").indexOf("win") >= 0;
    }
    private String getIORFilePath() {
        if (this.isWindows()) {
            return this.configuration.winPath;
        } else {
            return this.configuration.linuxPath;
        }
    }
    private String getIORXPath() {
        return this.configuration.xPath;
    }
    private void updateIORFile(String ior) {
        String filePath = this.getIORFilePath();
        String xPath = this.getIORXPath();
        IORWriter writer = new IORWriter(filePath, xPath);
        writer.write(ior);
    }
    public void run() throws UserException {
        String[] orbArgs = new String[]{
            "-ORBInitialPort",
            String.valueOf(this.configuration.orbInitialPort)
        };
        // create and initialize the ORB
        ORB orb = ORB.init(orbArgs, null);

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

        // write IOR to XML file
        this.updateIORFile(orb.object_to_string(href));

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
    }
}
