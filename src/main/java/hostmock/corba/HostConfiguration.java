package hostmock.corba;

import java.util.Properties;

public class HostConfiguration {
    public final int orbInitialPort;
    public final String ansDir;
    public final String winPath;
    public final String linuxPath;
    public final String xPath;
    public HostConfiguration(Properties properties) {
        this.orbInitialPort = Integer.parseInt(properties.getProperty("corba.orbinitialport"));
        this.ansDir = properties.getProperty("corba.ansdir");
        this.winPath = properties.getProperty("corba.ior.winpath");
        this.linuxPath = properties.getProperty("corba.ior.linuxpath");
        this.xPath = properties.getProperty("corba.ior.xpath");
    }
}
