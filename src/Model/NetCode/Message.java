package Model.NetCode;

import Model.Security.Keys;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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
        System.out.println("jag är i send message");
        try {
            for (Node n : Node.getNodes()) {
                System.out.println("jag är i en cool loop");
                new Thread(new PeerConnectionThread(createStandardizedMessage(type, amount, receiver), n)).start();
            }
        }catch(NullPointerException e){
            System.out.println("bajs");
            return false;
        }
        System.out.println("korv");
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
            String signature = new BigInteger(1, Keys.generateSignature(message)).toString(16);
            return message+" "+Base64.getEncoder().encodeToString(Keys.getPair().getPublic().getEncoded())+" "+signature+"\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
