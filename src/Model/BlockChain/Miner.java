package Model.BlockChain;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Miner implements Runnable {

    @Override
    public void run() {
        while(true) { //run forever
            if (!Transaction.getUnusedTransactions().isEmpty()) { //begin new block mine
                long startTime = System.currentTimeMillis();
                System.out.println("STARTING NEW BLOCK");
                ArrayList<Transaction> txs = Transaction.getUnusedTransactions();
                ArrayList<Transaction> txscopy = (ArrayList<Transaction>) txs.clone();
                txs.clear();
                Random r = new Random();
                int val = r.nextInt(100);
                txscopy.add(new Transaction("Jakob","Jakob",val,"Signatur"));

                String lastBlock = BlockChain.getLastBlock();
                Block nextBlock = new Block(lastBlock,txscopy);

                ///TRY SOLVE DA BLOCK
                int counter = 0;
                while(lastBlock == BlockChain.getLastBlock()){
                    String message = getMessage(nextBlock,counter);
                    String digest = getDigest(message);
                  //  System.out.println("New digest: " + digest);
                    if(digest.length() > 1){
                        if(didStartWith(5,digest)){
                            System.out.println(digest);
                            System.out.println(message);
                            System.out.println("DID START WITH ZERO! ADDING TO BLOCKCHAIN");
                            System.out.println("TIME SPENT MINING: " + (System.currentTimeMillis() - startTime) + " MS.");

                            //Send broadcast
                            //Add to blockchain as last block
                            sendBlockBroadcast(message,digest);
                            BlockChain.addBlock(message,digest);

                            break;
                        }
                    }
                    counter++;
                }

            }else{
                return;
            }
        }
    }

    private void sendBlockBroadcast(String block,String digest){
        String message = "b " + block + "----" + digest;
        System.out.println("Sending: " + message);
        for(Node n : Node.getNodes()){
            new Thread(new PeerConnectionThread(message,n)).start();
        }
    }

    private boolean didStartWith(int nrZeros,String digest){
        for(int i = 0; i < nrZeros; i++){
            if(digest.charAt(i) != '0'){
                return false;
            }
        }
        return true;
    }

    private String getDigest(String message) {

        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");

           // System.out.println("Length of message: " + message.length());
            byte[] result = mDigest.digest(message.getBytes());
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < result.length; i++){
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "403"; //error
    }

    private String getMessage(Block b,int counter){
        String transactions = "";

        for(Transaction t : b.getTransactionPool()){
            transactions += t.toString();
        }

        String s = "";
        s += b.getPreviousBlock() + "---" + transactions + "---" + counter;

        return s;
    }
}
