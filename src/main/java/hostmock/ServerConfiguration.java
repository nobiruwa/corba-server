package hostmock;

import hostmock.corba.HostConfiguration;
import hostmock.service.ServiceConfiguration;

import java.util.Properties;

public class ServerConfiguration {
    public final HostConfiguration corba;
    public final ServiceConfiguration service;
    public ServerConfiguration(Properties properties) {
        this.corba = new HostConfiguration(properties);
        this.service = new ServiceConfiguration(properties);
    }

}
