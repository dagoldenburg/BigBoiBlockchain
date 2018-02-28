package Model.NetCode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PeerHandlerThread implements Runnable {

    Socket connection;

    protected PeerHandlerThread(Socket connection){
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            DataOutputStream fromClient =
                    new DataOutputStream(connection.getOutputStream())


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
