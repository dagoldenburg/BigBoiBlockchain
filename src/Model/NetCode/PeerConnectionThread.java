package Model.NetCode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PeerConnectionThread implements Runnable {

    private String message;
    private Node node;
    public PeerConnectionThread(String message,Node node){
        this.message = message;
        this.node = node;
    }

    @Override
    public void run() {
        Socket messageSocket = new Socket();
        DataOutputStream toPeer = null;
        BufferedReader fromPeer = null;
        System.out.println("im a running boy");
        try {
            messageSocket.connect(new InetSocketAddress(node.getIp(),node.getPort()));
            toPeer = new DataOutputStream(messageSocket.getOutputStream());
            fromPeer = new BufferedReader(new InputStreamReader(messageSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Could not open connection to "+node.getIp()+":"+node.getPort());
        }
        try {
            System.out.println(" ska skcika de här "+message);
            toPeer.writeBytes(message);
            System.out.println("skickade och de va nice ");
            if(fromPeer.readLine().equals(0)){
                System.out.println("Transaction failed");
            }else System.out.println("Transaction successful");
            System.out.println("lol idn mamma ");
        } catch (NullPointerException|IOException e) {
            System.out.println("Sending failed to "+node.getIp()+":"+node.getPort());
        }finally{
            try {
                messageSocket.close();
                toPeer.close();
                fromPeer.close();
            } catch (NullPointerException|IOException e) {
            }
        }


    }

}
