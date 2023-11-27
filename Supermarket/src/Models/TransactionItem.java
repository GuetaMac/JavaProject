package Models;

public class TransactionItem {
    private int transactID;
    private Product p;

    public TransactionItem(int transactID, Product p, int quantity, double subtotal){
        this.transactID = transactID;
        this.p = p;
        this.p.updatePrice(subtotal/quantity);
        this.p.updateQuantity(quantity);
    }

    public Product getProduct(){
        return this.p;
    }
    
    public int getTransactID(){
        return this.transactID;
    }
}
