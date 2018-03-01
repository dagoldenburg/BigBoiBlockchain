package Model;

import java.util.LinkedList;

public class Block {

    Block previousBlock;
    LinkedList<Transaction> transactionPool;

    public Block(Block previousBlock, LinkedList<Transaction> transactionPool) {
        this.previousBlock = previousBlock;
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
}
