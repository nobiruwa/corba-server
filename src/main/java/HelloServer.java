import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.naming.InitialContext;
import javax.naming.Context;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;
import org.omg.CORBA.ORBPackage.InvalidName;

public class HelloServer {
    public void createORB(Servant servant) throws InvalidName, FileNotFoundException {
        String[] args = new String[4];
        args[0] = "-ORBInitialPort";
        args[1] = "1050";
        args[2] = "-ORBInitialHost";
        args[3] = "127.0.0.1";

        ORB orb = ORB.init(args, null);
        Object objPoa = orb.resolve_initial_references("RootPOA");
        POA rootPOA = org.omg.PortableServer.POAHelper.narrow(objPoa);
        Object obj = servant._this_object(orb);
        String ior = orb.object_to_string(obj);
        FileOutputStream file = new FileOutputStream("IORFile");
        PrintStream pFile = new PrintStream(file);
        pFile.println(ior);
    }
    public static void main(String[] args) {
        try {
            HelloImpl helloRef = new HelloImpl();
            Context initialNamingContext = new InitialContext();
            initialNamingContext.rebind("HelloService", helloRef);

            System.out.println("Hello Server: Ready...");
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
            e.printStackTrace();
        }
    }
}
