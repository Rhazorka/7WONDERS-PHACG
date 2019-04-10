package client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;

import com.google.gson.Gson;

import org.json.JSONObject;

import commun.Identification;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ClientTest{
    private Identification moi = new Identification();
    private Socket connexion;

    public String ClientTest(){
        return "client : réplique";
    }

    public String phrase_base;
    public String phrase_retour;

    public String getphrase_base(){
        return this.phrase_base;
    }

    public ClientTest(String urlServeur, String nom) {
        try {
            moi.setNom(nom);
            connexion = IO.socket(urlServeur);
            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    JSONObject id = new JSONObject(moi);
                    connexion.emit("identification", id); // transmet l'objet moi au serveur
                    phrase_base = ClientTest();
                    String Strjson = new Gson().toJson(phrase_base);
                    connexion.emit("test",Strjson); 
                }
            });
            
            connexion.on("retour_test", new Emitter.Listener(){
                @Override
                public void call(Object... objects){
                    phrase_retour = (String) objects[0];
                    System.out.println(phrase_retour);
                }
            });

            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    connexion.disconnect();
                    connexion.close();
                    System.exit(0);
                }
            });

        }catch (URISyntaxException e) {
                e.printStackTrace();
        }
        
    }

    public void seConnecter() {
        connexion.connect();
    }

    public static final void main(String []args) {
        String base_test = "client : réplique";
        String retour_test = "serveur : réplique reçu";

        ClientTest client_toto = new ClientTest("http://127.0.0.1:10101", "toto");
        client_toto.seConnecter();
        assertTrue(!base_test.equals(client_toto.phrase_base));
        assertTrue(!retour_test.equals(client_toto.phrase_retour));
    }
}

