package Model.BlockChain;

import java.util.ArrayList;

public class BlockChain {

    private static Block lastBlock = null;

    public static void addBlock(String b,String digest){
        //verify etc before adding
        //TODO: implement branches etc
        if(Block.isValid(b,digest)){

            String[] strs = b.split("---");
            if(strs.length > 0){
                if(lastBlock == null){
                    if(strs[0].equals("null")){
                        //FIRST BLOCK (ADD IT)
                        addNewBlock(lastBlock,getTransactionsFromString(strs[1]),digest);
                    }
                }else{
                    if(strs[0].equals(lastBlock.getDigest())){
                        //read transactions
                        addNewBlock(lastBlock,getTransactionsFromString(strs[1]),digest);
                    }else{
                        System.out.println("ERROR: DIDNT POINT TO PREVIOUS BLOCK IN CHAIN");
                    }
                }

            }else{
                System.out.println("UNEXPECTED ERROR");
            }
        }else{
            System.out.println("DID NOT ADD TO CHAIN");
        }
    }

    private static void addNewBlock(Block lastBlock, ArrayList<Transaction> transactions, String digest){
        Block newBlock = new Block(lastBlock,transactions,digest);
        BlockChain.lastBlock = newBlock;
        System.out.println("ADDED NEW BLOCK TO CHAIN");
    }

    private static ArrayList<Transaction> getTransactionsFromString(String transactionString){
        ArrayList<Transaction> list = new ArrayList<>();
        String[] strs = transactionString.split(" ");
        for(int i = 0; i < strs.length; i++){
            String[] data = strs[i].split("-");
            if(data.length >= 4){
                try{
                    Transaction t = new Transaction(data[0],data[1],Double.parseDouble(data[2]),data[3]);
                    list.add(t);
                }catch(Exception e){}
            }

        }
        return list;
    }

    public static void printBlockChain(){
        Block b = lastBlock;
        int i = 0;

        //since its a reversed linked list we gotta count the nr of blocks first if we wanna know the nr before printing
        while(b != null){ //get nr of blocks
            i++;
            b = b.getPreviousBlock();
        }

        b = lastBlock;
        while(b != null){ //print them out
            System.out.println("---------- BLOCK NR: " + i + " ----------");
            System.out.println("Proof of work: " + b.getDigest());
            System.out.println("Nr transactions: " + b.getTransactionPool().size());
            System.out.println("Previous block: " + b.getPreviousBlockDigest());
            System.out.println("------------------------------");
            System.out.println();
            b = b.getPreviousBlock();
            i--;
        }
    }
    public static Block getLastBlock(){
        return lastBlock;
    }
}
