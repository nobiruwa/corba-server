package helper;

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private List<byte[]> history;
    private Boolean running;
    private ExecutorService executor;

    public TCPServer(int portNumber) {
        this.history = Collections.synchronizedList(new ArrayList<byte[]>());
        this.executor = Executors.newSingleThreadExecutor();
        this.running = false;

        executor.submit(() -> {
                try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                    this.running = true;
                    while (this.running) {
                        Socket clientSocket = serverSocket.accept();

                        BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
                        ByteArrayOutputStream builder = new ByteArrayOutputStream();
                        byte[] inputData = new byte[1024];
                        int count = 0;
                        while ((count = in.read(inputData, 0, inputData.length)) != -1) {
                            builder.write(inputData, 0, count);
                        }
                        builder.flush();
                        this.history.add(builder.toByteArray());
                    }
                } catch (IOException e) {
                    e.printStackTrace(System.out);
                }
            });
    }

    public byte[][] getAll() {
        return this.history.toArray(new byte[this.history.size()][]);
    }

    public void clear() {
        this.history.clear();
    }

    public byte[][] receive() {
        byte[][] result = this.getAll();
        this.clear();
        return result;
    }

    public void stop() {
        this.running = false;
        this.executor.shutdownNow();
    }

    private void save(byte[] item) {
        this.history.add(item);
    }
}
