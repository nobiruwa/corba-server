package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloInterface extends Remote {
    public void sayHello(String from) throws RemoteException;
}
