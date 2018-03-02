package Model.BlockChain;

import java.util.ArrayList;

public class Block {

    String previousBlockDigest;
    ArrayList<Transaction> transactionPool;

    public Block(String previousBlock, ArrayList<Transaction> transactionPool) {
        this.previousBlockDigest = previousBlock;
        this.transactionPool = transactionPool;
    }

    /**
     * @param targetAccount
     * @return
     * Gets the balance from an account by looking through the block history.
     */
    public static double balance(String targetAccount) {
       return 0.0;
    }

    public void setPreviousBlock(String b){
        previousBlockDigest = b;

    }

    public String getPreviousBlock(){
        return previousBlockDigest;
    }

    public ArrayList<Transaction> getTransactionPool(){
        return (ArrayList<Transaction>) transactionPool.clone();
    }
}
