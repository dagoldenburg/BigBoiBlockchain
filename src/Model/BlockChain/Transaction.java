package Model.BlockChain;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;
import Model.Security.Keys;

import java.util.ArrayList;
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

    private static ArrayList<Transaction> unusedTransactions = new ArrayList<>();

    public static ArrayList<Transaction> getUnusedTransactions(){
        return unusedTransactions;
    }

    public static void addUnusedTransaction(Transaction t){
        if(t != null){
            unusedTransactions.add(t);
        }
    }

}
