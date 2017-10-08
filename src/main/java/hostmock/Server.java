package hostmock;

import java.lang.Void;
import java.lang.Thread;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final String PROPERTIES_FILE = "hostmock.properties";
    private static String loadProperties() {
        PropertiesLoader pLoader = new PropertiesLoader();
        return pLoader.searchClassLoader(PROPERTIES_FILE);
    }
    public static void main(String[] args) throws Exception {
        System.out.println(loadProperties());
        ExecutorService es = Executors.newFixedThreadPool(1);
        hostmock.service.HostServer serviceServer = new hostmock.service.HostServer();
        try {
            serviceServer.start(8090);
            es.execute(() -> {
                    try {
                        hostmock.corba.HostServer hostServer = new hostmock.corba.HostServer();
                        hostServer.run(1050);
                    } catch (Exception e) {
                        System.err.println("ERROR: " + e);
                        e.printStackTrace(System.out);
                    }
                });
        } finally {
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
            serviceServer.shutdown();
        }
    }
}
