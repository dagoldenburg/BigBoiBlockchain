package Model.BlockChain;

public class BlockChain {

    private static String lastBlock = "nada";

    public static void addBlock(String b,String digest){
        //verify etc before adding
        //TODO: implement branches etc
        if(Block.isValid(b,digest)){
            String[] strs = b.split("---");
            if(strs.length > 0){
                if(strs[0].equals(lastBlock)){
                    lastBlock = digest;
                    System.out.println("ADDED NEW BLOCK TO CHAIN");
                    Miner.sendBlockBroadcast("b "+b,digest);
                }else if(digest.equals(lastBlock)) {

                }else {
                    System.out.println("ERROR: DIDNT POINT TO PREVIOUS BLOCK IN CHAIN");
                }
            }else{
                System.out.println("UNEXPECTED ERROR");
            }
        }else{
            System.out.println("DID NOT ADD TO CHAIN");
        }
    }
    public static String getLastBlock(){
        return lastBlock;
    }
}
