package Model.NetCode;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenConnectionThread implements Runnable{

    private int port;

    public ListenConnectionThread(String port){
        this.port = Integer.parseInt(port);
    }


    /**
     * Listens for connection and starts a thread to handle communication with
     * the connecting peer.
     */
    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(port);
            while(true){
                Socket peerConnection = listener.accept();
                //Add node to nodes
                System.out.println("Received new node: " + peerConnection.getInetAddress().toString() + " " + peerConnection.getPort());
                new Thread(new PeerHandlerThread(peerConnection)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
