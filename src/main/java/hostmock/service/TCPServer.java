package hostmock.service;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class TCPServer {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TCPServer <port number>");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("accepted TCPClient...");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("TCPServer:");
                    System.out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}

