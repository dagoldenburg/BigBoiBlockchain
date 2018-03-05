package Model.NetCode;

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
     */
    @Override
    public void run() {
        try {
            String message = fromClient.readLine();
            System.out.println(message);

            if(message.startsWith("t")){ // transaction

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
            toClient.writeByte(message.length() > 10 ? 1 : 0);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                connection.close();
                fromClient.close();
                toClient.close();
            }catch(IOException e){

            }
        }
    }
}
