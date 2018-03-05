package Tests;

import Model.BlockChain.Miner;

public class BlockChainTests {

    public static void main(String[] argsv){
        //Transaction.addTransaction(new Transaction("Jakob","Daggen",1337,"SignatureN"));
        new Thread(new Miner()).start();
    }
}
