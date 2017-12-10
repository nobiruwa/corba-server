package helper;

import hostmock.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HostMockServer {
    private Boolean running;
    private ExecutorService executor;

    public HostMockServer() throws Exception {
        this.executor = Executors.newSingleThreadExecutor();
        this.running = false;

        executor.submit(() -> {
                try {
                    Server.main(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    public void stop() {
        this.running = false;
        this.executor.shutdownNow();
    }
}
