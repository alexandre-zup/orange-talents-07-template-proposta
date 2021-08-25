package br.com.zupacademy.propostas.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CriptografiaTest {

    @Test
    public void testa() {
        Criptografia cifrador = Criptografia.getInstance();
        String textoLimpo = "teste123";
        String hashTeste123 = "535f56e6447ea0fcf3ef1bf5397066d037e9ebb7fd141068e8de9a23ece8eb" +
                "6e7acf46d0e6bbf17edf2ebe6c80405991be53366138e835c3153019f164340619";

        String crypt1 = cifrador.cifrar(textoLimpo);
        String crypt2 = cifrador.cifrar(textoLimpo);

        String decrypt1 = cifrador.decifrar(crypt1);
        String decrypt2 = cifrador.decifrar(crypt2);

        String hashGerado = cifrador.gerarHash(textoLimpo);

        Assertions.assertNotEquals(crypt1, crypt2);
        Assertions.assertEquals(textoLimpo, decrypt1);
        Assertions.assertEquals(textoLimpo, decrypt2);
        Assertions.assertEquals(decrypt1, decrypt2);
        Assertions.assertEquals(hashTeste123, hashGerado);
    }

}
