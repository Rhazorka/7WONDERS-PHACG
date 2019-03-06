package client;

import static org.junit.Assert.*;
import org.junit.Test;
import client.Client;

public class ClientTest {

	@Test
    public void TestClient() throws Exception {
        Client client2 = new Client("http://127.0.0.1:10101");
        client2.seConnecter();
        assertTrue(client2.getTest());
    }

}
