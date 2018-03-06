package Model.BlockChain;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;
import Model.Security.Keys;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

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

    public static boolean duplicateTransaction(String signature){
        for(Transaction t : unusedTransactions){
            if(t.signature.equals(signature)){
                return true;
            }
        }
        return false;
    }

    private static List<Transaction> unusedTransactions = Collections.synchronizedList(new ArrayList<>());

    public static List<Transaction> getUnusedTransactions(){
        return unusedTransactions;
    }

    public static void clearTransactions(){
        unusedTransactions.clear();
    }

    @Override
    public String toString(){
        String s = "";
        s += " " + from + "-" + to + "-" + amount + "-" + signature;
        return s;
    }

    public static void addUnusedTransaction(Transaction t){
        if(t != null){
            unusedTransactions.add(t);
        }
    }

}
