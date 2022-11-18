package pteidsample;
//import pt.gov.cartaodecidadao.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static {
        try {
            System.loadLibrary("pteidlibj");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. \n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
getsysteminfo();
        // Cifras: Algoritmo/MododeCifra/Padding


    }



/*
    public static void readIDCard() {
        try {
            PTEID_ReaderSet.initSDK();

            PTEID_EIDCard card;
            PTEID_ReaderContext context;
            PTEID_ReaderSet readerSet;
            readerSet = PTEID_ReaderSet.instance();
            for (int i = 0; i < readerSet.readerCount(); i++) {
                context = readerSet.getReaderByNum(i);
                if (context.isCardPresent()) {
                    card = context.getEIDCard();
                    PTEID_EId eid = card.getID();
                    String nome = eid.getGivenName();

                    System.out.println("NOME: " + nome);
                    //(...)
                }
            }
            PTEID_ReaderContext readerContext = PTEID_ReaderSet.instance().getReader();

            PTEID_ReaderSet.releaseSDK();
        } catch(
        Exception e) {
            System.err.println("Native code library failed to load. \n" + e);
            System.exit(1);
        }
    }*/
    public static void generateKey() throws NoSuchAlgorithmException {
        String keyFname = null;
        String cipherAlgorit = null;
        String keySize = null;
        Scanner sc = new Scanner(System.in);

        System.out.print("Tipo de algoritmo [DES/AES]: ");
        cipherAlgorit = sc.next();
        if (cipherAlgorit == "AES") {
            System.out.print("Tamanho da Chave [128,192,256]: ");
            keySize = sc.next();
        }
        // Generate Key
        KeyGenerator keyUser = KeyGenerator.getInstance(cipherAlgorit);
        if (cipherAlgorit == "AES") keyUser.init(Integer.parseInt(keySize)); // Default 128
        SecretKey secretKeyUser = keyUser.generateKey();
    }
    public static void cipherFile(String fileName, SecretKey key, String algorithm) {
        String cipherFname = null;
        try {
            Scanner cipherFile = new Scanner(System.in);
            System.out.print("Nome do ficheiro para cifrar [cipher]: ");
            cipherFname = cipherFile.next();

            // Ler ficheiro (em bytes)
            FileInputStream textToCipher = new FileInputStream("./test/"+cipherFname);
            byte[] plaintext = new byte[textToCipher.available()];
            textToCipher.read(plaintext);

            // Encriptar
            Cipher cipherUser = Cipher.getInstance(algorithm);
            cipherUser.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValByUser = cipherUser.doFinal(plaintext);

            // Escrever ficheiro (em bytes)
            FileOutputStream saveCipheredText = new FileOutputStream("./test/"+fileName);
            saveCipheredText.write(encValByUser);
            saveCipheredText.close();

        } catch(Exception e) {
            System.err.println("Error! " + e.getMessage());
        }
    }
    public static void decipherFile() throws FileNotFoundException {
        String decipherFname = null;

        // Give file
        Scanner decipherFile = new Scanner(System.in);
        System.out.print("Nome do ficheiro para decifrar [decipher]: ");
        decipherFname = decipherFile.next();

        // Ler ficheiro (em bytes)
        FileInputStream textToDecipher = new FileInputStream("./test/"+decipherFname);
    }
    public static void getsysteminfo(){
        Map<String, String> map = System.getenv();
        map.entrySet().forEach(System.out::println);
    /*    System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
        System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
        System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
*/

    }

    private String encodeFileToBase64Binary(File file)
            throws IOException {

        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.getEncoder().encode(bytes);
        String encodedString = new String(encoded);

        return encodedString;
    }

    private byte[] loadFile(File file) throws IOException {
        byte[] bytes;
        try (InputStream is = new FileInputStream(file)) {
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                throw new IOException("File to large " + file.getName());
            }
            bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        }
        return bytes;
    }

}