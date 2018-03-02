package Model.BlockChain;

import Model.Block;

import java.util.LinkedList;

public class BlockChain {
    private static String lastBlock = "";

    public static void addBlock(String b){
        //verify here??
        lastBlock = b;
    }

    public static String getLastBlock(){
        return lastBlock;
    }
}
