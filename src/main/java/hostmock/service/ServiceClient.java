package hostmock.service;

import hostmock.PropertiesLoader;
import hostmock.SharedSingleton;

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
        HttpURLConnection con = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://localhost:" + String.valueOf(configuration.port) + "/");
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
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
    private static void ex() throws IOException {
        ServiceConfiguration configuration = SharedSingleton.getInstance().configuration.service;
        HttpURLConnection con = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://localhost:" + String.valueOf(configuration.port) + "/ex");
            String parameter = "exname=" + "sample" + "&exaddress=" + configuration.exAddress + "&export=" + String.valueOf(configuration.exPort) + "&expath=" + String.valueOf(configuration.exPath);
            byte[] postData = parameter.getBytes("UTF-8");
            con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestProperty("Content-Length", Integer.toString(postData.length));
            con.setUseCaches(false);
            try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
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
