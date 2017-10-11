package hostmock;

import hostmock.corba.HostServer;
import hostmock.service.ServiceServer;
import hostmock.CacheMap;
import hostmock.SharedSingleton;

import java.lang.Void;
import java.lang.Thread;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerConfiguration configuration = SharedSingleton.getInstance().configuration;
        CacheMap cachedAns = SharedSingleton.getInstance().cachedAns;
        CacheMap cachedEx = SharedSingleton.getInstance().cachedEx;
        ExecutorService es = Executors.newFixedThreadPool(1);
        ServiceServer serviceServer = new ServiceServer(configuration.service, cachedEx);
        try {
            java.util.logging.Logger.getLogger("org.glassfish.grizzly").setLevel(java.util.logging.Level.ALL);
            serviceServer.start();
            Future<?> future = es.submit(() -> {
                    try {
                        HostServer hostServer = new HostServer(configuration.corba, cachedAns);
                        hostServer.run();
                    } catch (Exception e) {
                        System.err.println("ERROR: " + e);
                        e.printStackTrace(System.out);
                    }
                });
            Object a = future.get();
        } finally {
            es.shutdownNow();
            serviceServer.shutdown();
        }
    }
}
