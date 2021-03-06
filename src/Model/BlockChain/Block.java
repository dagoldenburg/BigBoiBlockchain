package Model.BlockChain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Block {

    private String digest;
    private String previousBlockDigest;
    private ArrayList<Transaction> transactionPool;
    private Block previousBlock;

    public Block(){
        previousBlock = null;
        transactionPool = null;
        previousBlockDigest = null;
        digest = null;
    }

    public Block(Block previousBlock, ArrayList<Transaction> transactionPool,String digest) {
        this.previousBlock = previousBlock;
        this.transactionPool = transactionPool;
        this.digest = digest;
    }

    public String getDigest(){
        return digest;
    }

    public void setDigest(String digest){
        this.digest = digest;
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

    public Block getPreviousBlock(){
        return previousBlock;
    }

    public static boolean isValid(String block, String digest){
        System.out.println("VERIFYING..");
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            // System.out.println("Length of message: " + message.length());
            byte[] result = mDigest.digest(block.getBytes());
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < result.length; i++){
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            String s = sb.toString();
            if(s.equals(digest)){
                System.out.println("SUCCESS");
                return true;
            }else{
                System.out.println("FAIL");
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPreviousBlockDigest(){
        if(previousBlock == null){
            return "null";
        }else{
            return previousBlock.getDigest();
        }
    }

    public ArrayList<Transaction> getTransactionPool(){
        return (ArrayList<Transaction>) transactionPool.clone();
    }
}
