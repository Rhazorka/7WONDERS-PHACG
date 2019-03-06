package client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClientTest {

	@Test
    public void TestClient() throws Exception {

        Thread client1 = new Thread(new Runnable() {
            boolean test = false;
            @Override
            public void run() {
                test = true;
                assertTrue(test);
            }
        });
        client1.start();

    }

}