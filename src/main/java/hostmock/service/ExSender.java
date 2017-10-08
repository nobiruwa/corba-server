package hostmock.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ExSender {
    private String exAddress;
    private int exPort;
    private int exPath;
    public ExSender(String exAddress, int exPort, int exPath) {
        this.exAddress = exAddress;
        this.exPort = exPort;
        this.exPath = exPath;
    }
    public void send(String ex) {
        try (Socket socket = new Socket(this.exAddress, this.exPort)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("connecting to TCPServer...");
            out.println(ex);
        } catch (UnknownHostException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
