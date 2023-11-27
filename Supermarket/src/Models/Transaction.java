package Models;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Transaction {
    private Timestamp orderDateTime;
    private User buyer;
    private ArrayList<Product> items;
    private int transactID;
    private double donation;

    public Transaction(int transactID, User buyer, ArrayList<Product> items, Timestamp orderDateTime, double donation){
        this.transactID = transactID;
        this.buyer = buyer;
        this.items = items;
        this.orderDateTime = orderDateTime;
        this.donation = donation;
    }

    public Transaction(User buyer, ArrayList<Product> items, double donation){
        this.transactID = -1;
        this.buyer = buyer;
        this.items = items;
        this.orderDateTime = null;
        this.donation = donation;
    }

    public double getDonation(){
        return this.donation;
    }

    public int getTransactID(){
        return this.transactID;
    }

    public Timestamp getOrderDateTime(){
        return this.orderDateTime;
    }

    public User getBuyer(){
        return this.buyer;
    }

    public double getSubtotal(){
        double subtotal = 0;
        for(int i = 0; i < this.items.size(); i++)
            subtotal += this.items.get(i).getSubtotal();
        return subtotal;
    }

    public ArrayList<Product> getItems(){
        return this.items;
    }

    public int getQuantity(){
        int quantity = 0;
        for(int i = 0; i < this.items.size(); i++)
            quantity += this.items.get(i).getQuantity();
        return quantity;
    }

    public String getSummary(){
        String orderDateTime = "N/A";
        if(this.orderDateTime != null)
            orderDateTime = this.orderDateTime.toString();
        return "Order Date & Time: " + orderDateTime + "\nTransaction ID: " + this.transactID + "\nQuantity: " + this.getQuantity() + "\nSubtotal: Php" + this.getSubtotal() + "\nDonation: Php" + this.getDonation() + "\n\nItems:\n";
    }

    public String getVerboseSummary(){
        String bar = "========================================\n";
        String output = this.buyer.getSummary() + "\n" + getSummary();
        for(int i = 0; i < this.items.size(); i++)
            output += "    - " + this.items.get(i).getSummary() + "\n";
        return bar + output + bar;
    }

    public void printVerboseSummary(){
        System.out.println(getVerboseSummary());
    }

    public void printSummary(){
        System.out.println(getSummary());
    }
}
