package Model;

public class Transaction {

    private String from;
    private String to;
    private double amount;
    private String signature;

    public Transaction(String from, String to, double amount, String signature) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signature;
    }
}
