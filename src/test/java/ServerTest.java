import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import hostmock.service.resource.ExRequest;
import hostmock.service.resource.ExWithBodyRequest;

import helper.HTTPClient;
import helper.TCPServer;
import helper.ORBD;
import helper.HostMockServer;

import java.lang.Thread;

public class ServerTest {
    static HTTPClient httpClient;
    static TCPServer tcpServer;
    static ORBD orbd;
    static HostMockServer server;

    @BeforeClass
    public static void beforeClass() throws Exception {
        orbd = new ORBD("127.0.0.1", "23001");
        httpClient = new HTTPClient();
        tcpServer = new TCPServer(3000);

        Thread.sleep(3000);

        server = new HostMockServer();

        Thread.sleep(3000);
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
        tcpServer.stop();
        orbd.stop();
    }

    @Before
    public void before() {
        tcpServer.clear();
        httpClient.request("http://127.0.0.1:18090/ex/sample", "DELETE");
    }

    @Test
    public void post_ex_sample() throws Exception {
        byte[] b = httpClient.request("http://127.0.0.1:18090/ex/sample", "POST");

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in ex", new String(b, "windows-31j"));

        // TCP通信のチェック
        byte[][] exs = tcpServer.receive();

        assertEquals(exs.length, 1);
        assertEquals("This is a sample sentence!\n", new String(exs[0], "windows-31j"));
    }

    @Test
    public void put_ex_sample() throws Exception {
        ExWithBodyRequest req = new ExWithBodyRequest();
        req.exname = "sample";
        req.body = "This is a overwritten sentence!";

        byte[] b = httpClient.request("http://127.0.0.1:18090/ex/sample", "PUT", req);

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in cache", new String(b, "windows-31j"));

        // TCP通信のチェック
        byte[][] exs = tcpServer.receive();
        assertEquals(exs.length, 0);

        // PUT後のPOSTのチェック
        b = httpClient.request("http://127.0.0.1:18090/ex/sample", "POST");

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in cache", new String(b, "windows-31j"));

        // TCP通信のチェック
        exs = tcpServer.receive();

        assertEquals(exs.length, 1);
        assertEquals(new String(exs[0], "windows-31j"), "This is a overwritten sentence!\n");
    }

    @Test
    public void delete_ex_sample() throws Exception {
        ExWithBodyRequest req = new ExWithBodyRequest();
        req.exname = "sample";
        req.body = "This is a overwritten sentence!";

        byte[] b = httpClient.request("http://127.0.0.1:18090/ex/sample", "PUT", req);

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in cache", new String(b, "windows-31j"));

        // TCP通信のチェック
        byte[][] exs = tcpServer.receive();
        assertEquals(exs.length, 0);

        // PUT後のPOSTのチェック
        b = httpClient.request("http://127.0.0.1:18090/ex/sample", "POST");

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in cache", new String(b, "windows-31j"));

        // TCP通信のチェック
        exs = tcpServer.receive();

        assertEquals(exs.length, 1);
        assertEquals(new String(exs[0], "windows-31j"), "This is a overwritten sentence!\n");

        // DELETE
        b = httpClient.request("http://127.0.0.1:18090/ex/sample", "DELETE");

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in cache", new String(b, "windows-31j"));

        // TCP通信のチェック
        exs = tcpServer.receive();

        assertEquals(exs.length, 0);

        // DELETE後のPOSTのチェック
        b = httpClient.request("http://127.0.0.1:18090/ex/sample", "POST");

        // レスポンスのチェック
        assertTrue(b.length >= 0);
        assertEquals("sample in ex", new String(b, "windows-31j"));

        // TCP通信のチェック
        exs = tcpServer.receive();

        assertEquals(exs.length, 1);
        assertEquals(new String(exs[0], "windows-31j"), "This is a sample sentence!\n");
    }

}
