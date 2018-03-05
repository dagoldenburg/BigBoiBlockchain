package Model.BlockChain;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;
import Model.Security.Keys;

import java.util.Base64;

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

    @Override
    public String toString(){
        String s = "";

        s += " " + from + "-" + to + "-" + amount + "-" + signature;

        return s;
    }


}
