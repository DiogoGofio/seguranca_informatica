package pteidsample;
import pt.gov.cartaodecidadao.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
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
        try {
            readIDCard();
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


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
        } catch(Exception e) {
            System.err.println("Native code library failed to load. \n" + e);
            System.exit(1);
        }
    }
}