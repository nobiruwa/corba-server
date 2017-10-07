package hostmock;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            es.execute(() -> hostmock.corba.HostServer.main(args));
            es.execute(() -> hostmock.service.HostServer.main(args));
        } finally {
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
        }
    }
}
