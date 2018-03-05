package Model.NetCode;

import Model.BlockChain.Transaction;
import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;
import Model.Security.Keys;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Message {

    public final static char TYPE_TRANSACTION = 't';
    public final static char TYPE_BLOCK_SUGGEST = 'b';

    /**
     * @param amount
     * @param receiver Sends a transaction out to all known nodes for verification
     */
    public static boolean sendMessage(char type, String amount, String receiver) {
        try {
            String string = createStandardizedMessage(type, amount, receiver);
            if (string != null) {
                for (Node n : Node.getNodes()) {
                    new Thread(new PeerConnectionThread(string, n)).start();

                }
            } else {
                System.out.println("FAILED TO CREATE NEW TRANSACTION");
            }

        } catch (NullPointerException e) {
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
    private static String createStandardizedMessage(char type, String amount, String receiver) {
        String message = type + " " + amount + " " + receiver;
        try {
            // String signature = new BigInteger(1, Keys.generateSignature(message)).toString(16);
            String s = bytesToHex(Keys.generateSignature(message));
            System.out.println(s);
            String me = Base64.getEncoder().encodeToString(Keys.getPair().getPublic().getEncoded());

            /** ADD TRANSACTION TO UNUSED TRANSACTIONS **/
            Transaction.addUnusedTransaction(new Transaction(me, receiver, Double.parseDouble(amount), s));

            return message + " " + me + " " + s + "\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
