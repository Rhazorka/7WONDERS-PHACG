package serveur;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServeurTest {

	@Test
    public void TestServeur() throws Exception {

        Thread serveur = new Thread(new Runnable() {
            boolean test = false;
            @Override
            public void run() {
                Serveur.main(null);
                test = true;
                assertTrue(test);
            }
        });
        serveur.start();

    }

}