package br.com.zupacademy.propostas.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Criptografia {
    private static Criptografia instance = null;
    private static final String variavelAmbiente = "CRIPTOGRAFIA_SECRET";
    private String secret;

    private Criptografia() {
        secret = System.getenv().get(variavelAmbiente);
    }

    public static synchronized Criptografia getInstance() {
        if(instance == null)
            instance = new Criptografia();

        return instance;
    }

    /**
     * Recebe uma String em texto limpo e retorna o valor cifrado
     *
     * @param textoLimpo texto limpo para ser cifrado
     * @return texto cifrado
     */
    public String cifrar(@NotBlank String textoLimpo) {
        Assert.hasText(textoLimpo, "Texto nao deve ser em branco");
        StandardPBEStringEncryptor encryptor = this.getEncryptor();
        return encryptor.encrypt(textoLimpo);
    }

    /**
     * Recebe uma String cifrada e retorna o valor decifrado (texto limpo)
     *
     * @param textoCifrado texto cifrado para ser decifrado
     * @return texto decifrado
     */
    public String decifrar(@NotBlank String textoCifrado) {
        Assert.hasText(textoCifrado, "Texto nao deve ser em branco");
        StandardPBEStringEncryptor encryptor = this.getEncryptor();
        return encryptor.decrypt(textoCifrado);
    }

    /**
     * Recebe uma String em texto limpo e retorna o hash gerado com SHA512
     *
     * @param textoLimpo texto limpo para gerar o hash
     * @return hash gerado
     */
    public String gerarHash(@NotBlank String textoLimpo) {
        Assert.hasText(textoLimpo, "Texto nao deve ser em branco");


        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(textoLimpo.getBytes(StandardCharsets.UTF_8));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    private StandardPBEStringEncryptor getEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secret);
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());
        return encryptor;
    }
}
