package Model.BlockChain;

public class BlockChain {
    private static String lastBlock = "";

    public static void addBlock(String b,String digest){
        //verify etc before adding
        //TODO: implement branches etc
        if(Block.isValid(b,digest)){
            String[] strs = b.split("---");
            if(strs.length > 0){
                if(strs[0].equals(lastBlock)){
                    System.out.println("Pointed to previous block! (Good)");
                    lastBlock = b;
                    System.out.println("Added block to chain!");
                }else{
                    System.out.println("Didnt point to previous block...");
                }
            }else{
                System.out.println("An error occurred while adding block to chain");
            }
        }else{
            System.out.println("Didnt add block to chain.");
        }

    }

    public static String getLastBlock(){
        return lastBlock;
    }
}
