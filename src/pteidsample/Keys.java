package pteidsample;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Keys {
    private void generateKeyPair() { // algorithms -> DiffieHellman || DSA || RSA
        try {
            //Creating KeyPair generator object
            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");

            //Initializing the KeyPairGenerator
            keyPair.initialize(2048);

            //Generate the pair of keys
            KeyPair pair = keyPair.generateKeyPair();

            //Getting the private key from the key pair
            PrivateKey privKey = pair.getPrivate();

            //Getting the public key from the key pair
            PublicKey publicKey = pair.getPublic();

            System.out.println("Keys generated");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static PrivateKey getPrivateKey() { // algorithm = "RSA"
        String privKeyFile = "cipheredFile.crypto";
        try {
            // READ "privateKey.cripto"
            FileInputStream readPrivKeyFile = new FileInputStream(privKeyFile);
            byte[] keyText = new byte[readPrivKeyFile.available()];

            Cipher cipher = Cipher.getInstance("RSA");
            byte[] serverEncodedPrivateKey = cipher.doFinal(keyText);

            // Encode: X509EncodedKeySpec || PKCS8EncodedKeySpec
            PKCS8EncodedKeySpec serverKeySpec = new PKCS8EncodedKeySpec(serverEncodedPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey serverPrivateKey = keyFactory.generatePrivate(serverKeySpec);

            // incomplete...
            return serverPrivateKey;

        } catch(Exception e) {
            System.err.println("Error! " + e.getMessage());
        }

        return null;
    }

}
