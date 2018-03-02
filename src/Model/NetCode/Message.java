package Model.NetCode;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;
import Model.Security.Keys;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;

public class Message {

    public final static char TYPE_TRANSACTION = 't';
    public final static char TYPE_BLOCK_SUGGEST = 'b';

    /**
     * @param amount
     * @param receiver
     * Sends a transaction out to all known nodes for verification
     */
    public static boolean sendMessage(char type, String amount,String receiver){
        try {
            for (Node n : Node.getNodes())
                new Thread(new PeerConnectionThread(createStandardizedMessage(type, amount, receiver), n)).start();
        }catch(NullPointerException e){
            return false;
        }
        return true;
    }

    /**
     * @param type
     * @param amount
     * @param receiver
     * @return The message to be sent plus the public key and the signature.
     * Creates a standardized message so that communication across the blockchain network is uniform.
     */
    private static String createStandardizedMessage(char type, String amount, String receiver)  {
        String message = type+" "+amount+" "+receiver;
        try {
            return message+" "+Base64.getEncoder().encodeToString(Keys.getPair().getPublic().getEncoded())+" "+Keys.generateSignature(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
