package Model.BlockChain;

import java.util.ArrayList;

public class UnusedTransactions {
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public static ArrayList<Transaction> getTransactions(){
        return transactions;
    }

    public static void addTransaction(Transaction t){
        if(t != null){
            transactions.add(t);
        }
    }
}
