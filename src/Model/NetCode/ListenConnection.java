package Model.NetCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenConnection {

    final private int port;
    ServerSocket listener;

    ListenConnection(String port) throws Exception{
            this.port = Integer.parseInt(port);
    }

    public void listen() throws IOException{
        listener = new ServerSocket(port);
        while(true){
            Socket peerConnection = listener.accept();
            Thread t = new Thread(new PeerHandlerThread(peerConnection));
            t.start();
        }
    }

    private void peerHandler(){

    }

}
