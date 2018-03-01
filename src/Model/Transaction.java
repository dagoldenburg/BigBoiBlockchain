package Model;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;

public class Transaction {

    private String from;
    private String to;
    private double amount;
    private String signature;

    public Transaction(String from, String to, double amount, String signature) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signature;
    }

    /**
     * @param amount
     * @param receiver
     * Sends a transaction out to all known nodes for verification
     */
    public static void transaction(String amount,String receiver){
        for(Node n : Node.getNodes()) {
            new Thread(new PeerConnectionThread("t" + amount + receiver, n)).start();
        }
    }

}
