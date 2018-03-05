import Model.BlockChain.Block;
import Model.BlockChain.Miner;
import Model.NetCode.Message;
import Model.NetCode.ListenConnectionThread;
import Model.NetCode.Node;
import Model.Security.Keys;

import java.util.Base64;
import java.util.Scanner;

public class ClientGUI {

    final static String WRITE_HELP = ", write /help to see available commands and arguments.";

    public static void main(String[] argsv){
        try {
            Keys.generateKeys();
            System.out.println(Base64.getEncoder().encodeToString(Keys.getPair().getPublic().getEncoded()));

        } catch (Exception e) {
            e.printStackTrace();
        }


        ////////****** START THREADS ****** /////////////
        new Thread(new ListenConnectionThread(argsv[0])).start();
        new Thread( new Miner()).start();



        //***********************************************


        System.out.println("\n=============BigBoiBlockchain=============\n"
                          +"Welcome to BigBoiBlockchain!\n"
                          +"Write /help to see what commands are available.\n"
                          +"==========================================");
        for(boolean running = true;running!=false;) {
            System.out.print("> ");
            Scanner input = new Scanner(System.in);
            String command = input.nextLine().toLowerCase();
            String[] strings = command.split(" ");
            try {
                switch (strings[0]) {
                    case ("transaction"):
                        if(!Message.sendMessage(Message.TYPE_TRANSACTION,strings[1], strings[2])){
                            System.out.println("Could not send message");
                        }
                        break;
                    case ("balance"):
                        Block.balance(strings[1]);
                        break;
                    case ("add_node"):
                        Node.addNode(strings[1], strings[2]);
                        break;
                    case ("list_nodes"):
                        Node.listNodes();
                        break;
                    case ("remove_node"):
                        if(!Node.removeNode(strings[1]))
                            System.out.println("Missing/Invalid arguments"+WRITE_HELP);
                        break;
                    case ("/help"):
                        help();
                        break;
                    case ("exit"):
                        running = false;
                    default:
                        System.out.println("Bad input"+WRITE_HELP);
                }
            }catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
                System.out.println("Missing/Invalid arguments"+WRITE_HELP);
            }
        }
    }

    public static void help(){
        System.out.println("\n=============Help=============\n" +
                "transaction -amount-(Decimal value) -receiver-(Public key)\n" +
                "balance -target_account-(Public key)\n" +
                "add_node -ip- -port-\n" +
                "list_nodes\n" +
                "remove_node -index-(Integer listed in list_nodes)\n" +
                "exit\n" +
                "==============================");
    }
}
