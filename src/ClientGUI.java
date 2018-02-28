import Model.NetCode.ListenConnection;

import java.text.NumberFormat;
import java.util.Scanner;

public class ClientGUI {

    public static void main(String[] argsv){
        //ListenConnection listener = new ListenConnection(argsv[0]);
        System.out.println("=============BigBoiBlockchain=============");
        System.out.println("Welcome to BigBoiBlockchain!");
        System.out.println("Write /help to see what commands are available.");
        System.out.println("==========================================");
        for(boolean running = true;running!=false;) {
            System.out.print("Input: ");
            Scanner input = new Scanner(System.in);
            String command = input.nextLine().toLowerCase();
            String[] strings = command.split(" ");
            try {
                switch (strings[0]) {
                    case ("transaction"):
                        transaction(strings[1], strings[2]);
                        break;
                    case ("balance"):
                        balance(strings[1]);
                        break;
                    case ("add_node"):
                        addNode(strings[1], strings[2]);
                        break;
                    case ("list_nodes"):
                        listNodes();
                        break;
                    case ("remove_node"):
                        removeNode(strings[1]);
                        break;
                    case ("/help"):
                        help();
                        break;
                    case ("exit"):
                        running = true;
                    default:
                        System.out.println("Bad input, write /help to see available commands.");
                }
            }catch(ArrayIndexOutOfBoundsException|NumberFormatException e){
                System.out.println("Missing/Bad arguments, write /help to see available commands and arguments.");
            }
        }
    }
    public static boolean transaction(String amount,String receiver) throws NumberFormatException {
        double parsedAmount = Double.parseDouble(amount);
        //TODO:send transaction to all known nodes.
        return true;
    }

    public static boolean balance(String targetAccount) {
        try{
            //TODO:get balance from chosen
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addNode(String ip, String port){
        try{

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean listNodes(){
        try{

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean removeNode(String index) throws NumberFormatException {
        int parsedIndex = Integer.parseInt(index);
        return true;
    }

    public static void help(){
        System.out.println("=============Help=============");
        System.out.println("transaction -amount-(Decimal value) -receiver-(Public key)");
        System.out.println("balance -target_account-(Public key)");
        System.out.println("add_node -ip- -port-");
        System.out.println("list_nodes");
        System.out.println("remove_node -index-(Integer listed in list_nodes)");
        System.out.println("exit");
        System.out.println("==============================");
    }
}
