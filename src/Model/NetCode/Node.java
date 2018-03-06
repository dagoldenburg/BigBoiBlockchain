package Model.NetCode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Node {
    String ip;
    int port;
    private static LinkedList<Node> nodes = new LinkedList<>();

    private Node(String ip, String port) throws NumberFormatException{
        this.port = Integer.parseInt(port);
        this.ip = ip;
    }
    private Node(String ip, int port){
        this.port = port;
        this.ip = ip;
    }

    /**
     * @return All nodes in nodes list.
     */
    public static LinkedList<Node> getNodes() {
        return nodes;
    }

    /**
     * @return Ip of Node
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return Port of node
     *
     */
    public int getPort() {
        return port;
    }

    /**
     * @param ip
     * @param port
     * @return True if it can be connected to, false if not.
     * Adds a node to the nodes list.
     */
    public static boolean addNode(String ip, String port,String localinfo){
        //TODO: See that node isnt already in the nodelist
        Node node = new Node(ip, port);
        new Thread(new PeerConnectionThread("c "+localinfo+"\n", node)).start();
        nodes.add(node);
        return true;
    }

    public static boolean addNode(String ip, String port){
        Node node = new Node(ip, port);
        nodes.add(node);
        return true;
    }

    public static boolean addNode(String ip,  int port,String localinfo){
        nodes.add(new Node(ip,port));
        return true;
    }
    /**
     * @param index
     * @return True if node was removed, false if it couldn't be.
     * Removes a node from the nodes list.
     */
    public static boolean removeNode(String index){
        try {
            nodes.remove(Integer.parseInt(index));
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Lists all nodes in the nodes list.
     */
    public static void listNodes(){
        StringBuilder sb = new StringBuilder();
        for (Node n : Node.getNodes()) {
            sb.append(n.getIp()+":"+n.getPort()+"\n");
        }
        System.out.println("\n"+sb.toString());
    }

}
