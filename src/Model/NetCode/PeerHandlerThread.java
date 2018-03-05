package Model.NetCode;

import Model.BlockChain.Transaction;
import Model.Security.Keys;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import Model.BlockChain.BlockChain;

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
            String message = fromClient.readLine();
            String[] strings = message.split(" ");
            if(strings[0].equals("t")){ // transaction, TODO:hantera NumberFormatException från parseDouble
                KeyFactory kf = KeyFactory.getInstance("EC");
                PublicKey pub = kf.generatePublic(new X509EncodedKeySpec(
                        Base64.getDecoder().decode(strings[3].getBytes())
                ));

                System.out.println(strings[4]);
                if(Keys.validateSignature(strings[0]+" "+strings[1]+" "+strings[2],
                        pub,hexStringToByteArray(strings[4]))){
                        toClient.writeByte(1);
                        Transaction.addUnusedTransaction(
                        new Transaction(strings[3],strings[2],Double.parseDouble(strings[1]),strings[4]));
                }else
                    toClient.writeByte(0);
            }else if(message.startsWith("b ")){ //suggestion for new blockchain
                System.out.println("Received new block");
                if(message.length() > 2){
                    String s = message.substring(2,message.length()-1);
                    String[] strs = s.split("----");
                    if(strs.length > 1){
                        String block = strs[0];
                        String digest = strs[1];
                        BlockChain.addBlock(block,digest);
                    }else{
                        System.out.println("Error when parsing string");
                    }
                }else{
                    System.out.println("Format failure");
                }

            }
        } catch (IOException | SignatureException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
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
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
