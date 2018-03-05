package Model.BlockChain;

import Model.NetCode.Node;
import Model.NetCode.PeerConnectionThread;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Miner implements Runnable {

    @Override
    public void run() {
        long lastUpdate = 0;
        while (true) { //run forever
            if (!Transaction.getUnusedTransactions().isEmpty()) { //begin new block mine
                long startTime = System.currentTimeMillis();
                System.out.println("NEW TRANSACTION(S) DETECTED");
                System.out.println("STARTING MINING OF NEW BLOCK");
                ArrayList<Transaction> txs = new ArrayList<>(Transaction.getUnusedTransactions());
                ArrayList<Transaction> txscopy = (ArrayList<Transaction>) txs.clone();
                Transaction.clearTransactions();
                
                Random r = new Random();
                int val = r.nextInt(100);
                txscopy.add(new Transaction("Jakob", "Jakob", val, "Signatur"));

                String lastBlock = BlockChain.getLastBlock();
                Block nextBlock = new Block(lastBlock, txscopy);

                ///TRY SOLVE DA BLOCK
                int counter = 0;
                while (true) {
                    String message = getMessage(nextBlock, counter);
                    String digest = getDigest(message);
                    System.out.println("New digest: " + digest);
                    if (digest.length() > 1) {
                        if (didStartWith(3, digest)) {
                            System.out.println("SUCCESSFULLY MINED BLOCK");
                            System.out.println("TIME SPENT MINING: " + (System.currentTimeMillis() - startTime) + " MS.");

                            //Send broadcast
                            //Add to blockchain as last block
                            sendBlockBroadcast(message, digest);
                            BlockChain.addBlock(message, digest);

                            break;
                        }
                    }
                    counter++;
                    if(lastBlock != BlockChain.getLastBlock()){
                        System.out.println("SOMEONE ELSE SOLVED IT BEFORE US, ABORTING MINING PROCESS");
                        break;
                    }
                }
            }else{
               // System.out.println("Was empty");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void sendBlockBroadcast(String block, String digest) {
        String message = "b " + block + "----" + digest + "-----\n";
        for (Node n : Node.getNodes()) {
            new Thread(new PeerConnectionThread(message, n)).start();
        }
    }

    private boolean didStartWith(int nrZeros, String digest) {
        for (int i = 0; i < nrZeros; i++) {
            if (digest.charAt(i) != '0') {
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
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "403"; //error
    }

    private String getMessage(Block b, int counter) {
        String transactions = "";

        for (Transaction t : b.getTransactionPool()) {
            transactions += t.toString();
        }

        String s = "";
        s += b.getPreviousBlock() + "---" + transactions + "---" + counter;

        return s;
    }
}
