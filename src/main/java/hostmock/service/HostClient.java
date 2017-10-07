package hostmock.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HostClient {
    public static void main(String[] args) throws IOException {
        HttpURLConnection con = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://localhost:8090/");
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
}
