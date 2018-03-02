package Model.NetCode;

import Model.BlockChain.Transaction;
import Model.Security.Keys;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerHandlerThread implements Runnable {

    private Socket connection;
    private BufferedReader fromClient;
    private DataOutputStream toClient;

    /**
     * @param connection
     * @throws IOException
     * Handles a connected client and intiates the in & out streams.
     */
    protected PeerHandlerThread(Socket connection) throws IOException{
        this.connection = connection;
        fromClient =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toClient =
                new DataOutputStream(connection.getOutputStream());
    }

    /**
     * Receives one message and returns one answer.
     * Answer depends on if the message can be correctly verified,
     * 1 if received message is verifiable, 0 if not.
     *
     * strings for transaction:
     * strings[0] = type
     * strings[1] = amount
     * strings[2] = receiver(public key)
     * strings[3] = public key of sender
     * strings[4] = signature
     */
    @Override
    public void run() {
        try {
            System.out.println("din mama e bög");
            String message = fromClient.readLine();
            System.out.println(message);
            String[] strings = message.split(" ");
            if(strings[0].equals("t")){ // transaction, TODO:hantera NumberFormatException från parseDouble
                Transaction.addUnusedTransaction(
                        new Transaction(strings[3],strings[2],Double.parseDouble(strings[1]),strings[4]));
            }else if(strings[0].equals("b")){ //suggestion for new blockchain

            }
            KeyFactory kf = KeyFactory.getInstance("EC");
            PublicKey pub = kf.generatePublic(new X509EncodedKeySpec(
                    Base64.getDecoder().decode(strings[3].getBytes())
            ));
            byte[] signature = new BigInteger(1,strings[4].getBytes()).toByteArray();
            toClient.writeByte(Keys.validateSignature(strings[0]+" "+strings[1]+" "+strings[2],
                    pub,signature) ? 1 : 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } finally{
            try {
                connection.close();
                fromClient.close();
                toClient.close();
            }catch(IOException e){

            }
        }
    }
}
