package hostmock.service;

import java.util.Properties;

public class ServiceConfiguration {
    public final int port;
    public final String exDir;
    public final String exAddress;
    public final int exPort;
    public final int exPath;
    public ServiceConfiguration(Properties properties) {
        this.port = Integer.parseInt(properties.getProperty("service.port"));
        this.exDir = properties.getProperty("service.exdir");
        this.exAddress = properties.getProperty("service.exaddress");
        this.exPort = Integer.parseInt(properties.getProperty("service.export"));
        this.exPath = Integer.parseInt(properties.getProperty("service.expath"));
    }
}
