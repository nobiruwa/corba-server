package hostmock;

import hostmock.CacheMap;
import hostmock.ServerConfiguration;
import hostmock.PropertiesLoader;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class SharedSingleton {
    private static final String PROPERTIES_FILE = "hostmock.properties";

    public final ServerConfiguration configuration;
    public final CacheMap cachedAns;
    public final CacheMap cachedEx;

    private Properties loadProperties() {
        PropertiesLoader pLoader = new PropertiesLoader();
        return pLoader.load(PROPERTIES_FILE);
    }
    private ServerConfiguration loadConfiguration(Properties properties) {
        return new ServerConfiguration(properties);
    }

    private static SharedSingleton instance;
    private SharedSingleton() {
        this.configuration = this.loadConfiguration(this.loadProperties());
        this.cachedAns = new CacheMap();
        this.cachedEx = new CacheMap();
    }
    public static synchronized SharedSingleton getInstance() {
        if(instance == null) {
            instance = new SharedSingleton();
        }
        return instance;
    }
}
