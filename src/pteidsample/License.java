package pteidsample;

import java.security.PrivateKey;

public class License {
    public void licenseRequest(String fileToDecipher, String algorithm) {
        try {
            // Busca chave privada
            PrivateKey serverKey = Keys.getPrivateKey();

            // Decifra com chave privada
            FileHandler.decipherFile("cipheredFile.crypto", serverKey, algorithm);

            // Valida os dados
            // Valida a chave do utilizador

            // Cria licença
            licenseCreate();

            // Assina licença (com chave publica do utilizador)

            // Enviar a licença

        } catch(Exception e) {
            System.err.println("Error! " + e.getMessage());
        }
    }

    public static void licenseCreate() {

    }
}
