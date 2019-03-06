package serveur;

import static org.junit.Assert.*;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;

import org.junit.Test;

public class ServeurTest {

	@Test
    public void TestClient() throws Exception {
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        // permet de réutiliser l'adresse du port (fix linux)
        SocketConfig sockConfig = new SocketConfig();
        sockConfig.setReuseAddress(true);
        config.setSocketConfig(sockConfig);

        Serveur serveur = new Serveur(config);
        serveur.demarrer();
        assertTrue(serveur.getTest());
    }

}