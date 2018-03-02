package Tests;

import Model.BlockChain.Miner;
import Model.BlockChain.UnusedTransactions;
import Model.Transaction;

public class BlockChainTests {

    public static void main(String[] argsv){
        UnusedTransactions.addTransaction(new Transaction("Jakob","Daggen",1337,"SignatureN"));
        new Thread(new Miner()).start();
    }
}
