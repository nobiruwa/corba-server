package helper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientResponse;

public class HTTPClient {
    private Client client;

    public HTTPClient() {
        this.client = ClientBuilder.newClient();
    }

    public byte[] request(String url, String method) {
        WebTarget webTarget = client.target(url);

        Response response = webTarget.request()
            .header("Content-Type", "application/json")
            .build(method)
            .invoke();

        if (response.getStatus() >= 300) {
            return null;
        }

        byte[] body = response.readEntity(byte[].class);

        return body;
    }
    public byte[] request(String url, String method, Object obj) {
        WebTarget webTarget = client.target(url);

        Response response = webTarget.request()
            .header("Content-Type", "application/json")
            .build(method, Entity.json(obj))
            .invoke();

        if (response.getStatus() >= 300) {
            return null;
        }

        byte[] body = response.readEntity(byte[].class);

        return body;
    }
}
