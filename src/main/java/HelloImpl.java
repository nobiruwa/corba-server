import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

public class HelloImpl extends PortableRemoteObject implements HelloInterface {
    public HelloImpl() throws RemoteException {
        super();
    }

    public void sayHello(String from) throws RemoteException {
        System.out.println("Hello from " + from + "!!");
        System.out.flush();
    }
}
