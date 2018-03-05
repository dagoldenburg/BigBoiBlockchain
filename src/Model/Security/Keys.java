package Model.Security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

public class Keys {

    private static KeyPair pair;

    public static KeyPair getPair() {
        return pair;
    }

    public static void generateKeys() throws NoSuchProviderException, NoSuchAlgorithmException {
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(256,SecureRandom.getInstance("SHA1PRNG"));
        pair = kpg.generateKeyPair();
    }

    public static byte[] generateSignature(String message)
            throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException {
        Signature ecdsa = Signature.getInstance("SHA256withECDSA");
        ecdsa.initSign(pair.getPrivate());
        byte[] strByte = message.getBytes("UTF-8");
        ecdsa.update(strByte);
        return ecdsa.sign();
    }

    public static boolean validateSignature(String message,PublicKey pub,byte[] signature)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature ecdsa = Signature.getInstance("SHA256withECDSA");
        ecdsa.initVerify(pub);
        ecdsa.update(message.getBytes("UTF-8"));
        return ecdsa.verify(signature);
    }

}
