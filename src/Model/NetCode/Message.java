package Model.NetCode;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;
import Model.Security.Keys;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

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
            String string = createStandardizedMessage(type, amount, receiver);
            for (Node n : Node.getNodes()) {
                new Thread(new PeerConnectionThread(string, n)).start();
            }
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
        if(type == 't') {
            try {
                String s = bytesToHex(Keys.generateSignature(amount + receiver));
                String string = Base64.getEncoder().encodeToString(Keys.getPair().getPublic().getEncoded());
                System.out.println(string);
                System.out.println("Do you want to change the sender key? y/n");
                Scanner scanner = new Scanner(System.in);
                if(scanner.nextLine().equals("y")){
                    final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
                    kpg.initialize(256, SecureRandom.getInstance("SHA1PRNG"));
                    KeyPair pair = kpg.generateKeyPair();
                    string = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
                    System.out.println(string);
                }
                return type +" "+amount+" "+receiver + " " + string + " " + s + "\n";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type == 'b') {

        }
        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
