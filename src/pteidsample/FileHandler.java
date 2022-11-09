package pteidsample;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

public class FileHandler {

    public static void cipherFile(String fileToCipher, PublicKey key, String algorithm) { // cipheredText, publicKey, RSA
        try {
            // Ler ficheiro (em bytes)
            FileInputStream textToCipher = new FileInputStream(fileToCipher);
            byte[] plaintext = new byte[textToCipher.available()];
            textToCipher.read(plaintext);

            // Encriptar
            Cipher cipherUser = Cipher.getInstance(algorithm);
            cipherUser.init(Cipher.ENCRYPT_MODE, key);
            byte[] encoded = cipherUser.doFinal(plaintext);

            // Escrever ficheiro (em bytes)
            FileOutputStream saveCipheredText = new FileOutputStream("cipheredFile.crypto");
            saveCipheredText.write(encoded);
            saveCipheredText.close();

        } catch(Exception e) {
            System.err.println("Error! " + e.getMessage());
        }
    }

    public static void decipherFile(String fileToDecipher, PrivateKey key, String algorithm) { // cipheredFile.crypto, privateKey, RSA
        try {
            // Ler ficheiro (em bytes)
            FileInputStream readFileCripto = new FileInputStream(fileToDecipher);
            byte[] plaintext = new byte[readFileCripto.available()];
            readFileCripto.read(plaintext);

            // Decifrar
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));

            byte[] encodedDecipher = cipher.doFinal(plaintext);

            // Gravar decifrado
            FileOutputStream saveDecipherFile = new FileOutputStream("cipheredFile.txt");
            saveDecipherFile.write(encodedDecipher);
            saveDecipherFile.close();

        } catch(Exception e) {
            System.err.println("Error! " + e.getMessage());
        }
    }
}
