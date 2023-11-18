package Models;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void updatePrice(double newPrice){
        this.price = newPrice;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Get the discounted price of the product given the discount % in decimal (e.g., 90% = 0.90)
     * @param discount
     * @return Discounted price of the item.
     */
    public double discountPrice(double discount){
        return this.price - (this.price * discount);
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }
    
    public int getQuantity(){
        return this.quantity;
    }

    public double getSubtotal(){
        return this.quantity * this.price;
    }

    public String getSummary(boolean isCustomer){
        if (isCustomer)
            return this.name + " - Php " + this.getPrice() + " @ " + this.quantity + "pc(s) = " + this.getSubtotal();
        return this.name + " - Php " + this.getPrice() + " @ " + this.quantity + "pc(s).";
    }
}