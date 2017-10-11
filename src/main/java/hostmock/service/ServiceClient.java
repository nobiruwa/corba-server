package hostmock.service;

import hostmock.PropertiesLoader;
import hostmock.SharedSingleton;
import hostmock.service.resource.ExRequest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class ServiceClient {
    private static void hello() throws IOException {
        ServiceConfiguration configuration = SharedSingleton.getInstance().configuration.service;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:" + String.valueOf(configuration.port) + "/");
        String res = target.request().get(String.class);
        System.out.println("result = " + res);
    }
    private static void ex() throws IOException {
        ServiceConfiguration configuration = SharedSingleton.getInstance().configuration.service;
        // ExRequest req = new ExRequest();
        // req.setExName("sample");
        // req.setExAddress(configuration.exAddress);
        // req.setExPort(configuration.exPort);
        // req.setExPath(configuration.exPath);
        // Client client = ClientBuilder.newClient();
        // WebTarget target = client.target("http://localhost:" + String.valueOf(configuration.port)).path("ex");
        // Response res = target
        //     .request(MediaType.APPLICATION_JSON)
        //     .post(Entity.entity(req, MediaType.APPLICATION_JSON));
        // System.out.println("result = " + res);

        HttpURLConnection con = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://localhost:" + String.valueOf(configuration.port) + "/ex/sample");
            // String parameter = "exname=" + "sample" + "&exaddress=" + configuration.exAddress + "&export=" + String.valueOf(configuration.exPort) + "&expath=" + String.valueOf(configuration.exPath);
            // byte[] postData = parameter.getBytes("UTF-8");
            con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            // con.setRequestProperty("Content-Length", Integer.toString(postData.length));
            con.setUseCaches(false);
            // try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            //     wr.write(postData);
            // }
            con.connect();

            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                System.out.println(status);
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        System.out.println("result=" + result.toString());
    }
    public static void main(String[] args) {
        try {
            hello();
            ex();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
