package Tests;

import Model.BlockChain.Miner;
import Model.BlockChain.UnusedTransactions;
import Model.BlockChain.Transaction;

public class BlockChainTests {

    public static void main(String[] argsv){
        //Transaction.addTransaction(new Transaction("Jakob","Daggen",1337,"SignatureN"));
        new Thread(new Miner()).start();
    }
}
