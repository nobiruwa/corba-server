package hostmock.corba;

import java.util.Properties;

public class HostConfiguration {
    public final int orbInitialPort;
    public final String ansDir;
    public HostConfiguration(Properties properties) {
        this.orbInitialPort = Integer.parseInt(properties.getProperty("corba.orbinitialport"));
        this.ansDir = properties.getProperty("corba.ansdir");
    }
}
