package Drivers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Models.*;
import Views.*;

public class Controller implements ActionListener {
    private SQL sql;
    private GUI g;
    private User loggedUser;
    private ArrayList<Product> cart;
    
    public Controller(GUI g){
        this.sql = new SQL();
        this.g = g;
        this.g.setController(this);
        cart = new ArrayList<Product>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();

        if (act.equals("loginCommand")) {
            System.out.println("Login");
            Login l = this.g.getLoginPage();
            int loginResult = sql.checkCredential(l.getUsername(), l.getPassword());
        
            if (loginResult != -1) {
                // Valid login
                loggedUser = sql.getUser(l.getUsername(), l.getPassword());
        
                // Check user type and switch to the appropriate page
                if (loggedUser.getType() == 0) {
                    System.out.println("Employee");
                    this.g.switchToEmployeePage(loggedUser.getUsername());
                } else if (loggedUser.getType() == 1) {
                    System.out.println("Customer");
                    this.g.switchToCustomerPage(loggedUser.getUsername());
                }
            } else {
                // Invalid login
                System.out.println("Invalid credentials. Please check your username and password.");
                new Popup().warning("Error", "Invalid credentials. Please check your username and password.");

            }
        }
        
        if(act.equals("exitCommand")){
			System.exit(0);
        }

        if(act.equals("signupCommand")){
            User u = new Popup().newUser(this);
            if(u != null){
                sql.addUser(u);
            }
        }

        if(act.equals("logoutCommand")){
			System.out.println("Logout");
            this.g.switchToLogin();
            loggedUser = null;
            cart.clear();
        }

        if(act.equals("addStockCommand")){
			Employee employee = this.g.getEmployeePage();
            String productName = employee.getProductName();
            double productPrice = employee.getProductPrice();
            int productQuantity = employee.getProductQuantity();
            Product p = new Product(productName, productPrice, productQuantity);
            this.sql.addProduct(p);
            this.g.getEmployeePage().updateProductList(getProducts());
        }
        
        if(act.equals("editStockCommand")){
			System.out.println("Edit Product");
            Product p = sql.getProduct(this.g.getEmployeePage().getProductName());
            if(p == null){
                new Popup().warning("Search Item", "No item: \"" + this.g.getEmployeePage().getProductName() + "\"");
            }else{
                //update the backend(data) then update the front-end(gui/ui)
                p.updatePrice(this.g.getEmployeePage().getProductPrice());
                p.updateQuantity(this.g.getEmployeePage().getProductQuantity());

                sql.editProduct(p);
                this.g.getEmployeePage().setProductInfo(p);
                this.g.getEmployeePage().updateProductList(getProducts());
            }
        }

        if (act.equals("removeStockCommand")) {
            System.out.println("Remove Stock");
            Employee employee = this.g.getEmployeePage();
            String productName = employee.getProductName();
            boolean removed = this.sql.removeProduct(productName);
            if (removed) {
                System.out.println("Product Removed!");
            } else {
                System.out.println("Product Not Removed!");
                new Popup().warning("Warning", "Product Not Removed");
            }
            this.g.getEmployeePage().updateProductList(getProducts());
        }

        if (act.equals("searchStockCommand")) {
           System.out.println("Search Product");
            // Assuming that the Employee class has a method getProductName() to get the product name
            Employee employee = this.g.getEmployeePage();
            String productName = employee.getProductName();
        
            // Assuming that the SQL class has methods getProduct and searchProduct
            Product p = this.sql.getProduct(productName);

            this.g.getEmployeePage().setProductInfo(p);
        
            // Assuming updateProductList method updates the product list on the Employee page
            this.g.getEmployeePage().updateProductList(getProducts());
        }
    
        if (act.equals("addToCartCommand")) {
            System.out.println("Add Cart");
        
            String productName = this.g.getCustomerPage().getProductName();
            int requestedQuantity = this.g.getCustomerPage().getProductQuantity();
        
            // Get the product from the database
            Product targetProduct = sql.getProduct(productName);
        
            // Check if the requested quantity is available
            if (targetProduct.getQuantity() >= requestedQuantity) {
                // Update the quantity in the sampled Product object
                targetProduct.updateQuantity(requestedQuantity);
        
                // Check if there is sufficient quantity after updating
                if (targetProduct.getQuantity() > 0) {
                    // Add the product to the cart
                    cart.add(targetProduct);
                    this.g.getCustomerPage().updateCartList(cart);
                }
            } else {
                System.out.println("Error: Insufficient stock for " + productName);
                new Popup().warning("Waring!", "Error: Insufficient stock for that item");
            }
            // Note that the product list won't update based on the contents of the cart
            // as the items in the cart are not necessarily items that were checked out.
        }
        

        if(act.equals("removeFromCartCommand")){
            System.out.println("Remove From Cart");
            Product targetProduct = sql.getProduct(this.g.getCustomerPage().getProductName());
            targetProduct.updateQuantity(this.g.getCustomerPage().getProductQuantity()); //<== this won't yet change the contents of DB nor the product list as it is a sampled Product object which will be used to list the productss in the cart.
            targetProduct.updatePrice(targetProduct.getPrice()*targetProduct.getQuantity());
            cart.remove(targetProduct);
            this.g.getCustomerPage().updateCartList(cart);
        }

        if (act.equals("checkoutCommand")) {
            // ask for donation pop-up
            double donation = new Popup().askDonation();
        
            System.out.println("Checkout na!");
            String buyer = loggedUser.getUsername();
            double productSubtotal = 0;
            int quantity = 0;
            for (int p = 0; p < cart.size(); p++) {
                productSubtotal += cart.get(p).getSubtotal();
                quantity += cart.get(p).getQuantity();
            }
            String prodname = "";
            this.sql.addTransaction(buyer, productSubtotal + donation, quantity, prodname);
        
            /**
             * 1. For every product in cart<Product>, deduct quantity stipulated to the database.
             */
            for (int p = 0; p < cart.size(); p++) {
                int deductQuantity = cart.get(p).getQuantity();
                sql.deductProduct(cart.get(p), deductQuantity);
            }
        
            cart.clear();
            this.g.getCustomerPage().clearCart();
        
            this.g.getCustomerPage().updateProductList(getProducts());
        }
               
        
        if (act.equals("searchProductCommand")) {
            System.out.println("Search Product");
        
            // Assuming that the Employee class has a method getProductName() to get the product name
            Customer customer = this.g.getCustomerPage();
            String productName = customer.getProductName();
        
            // Assuming that the SQL class has methods getProduct and searchProduct
            Product p = this.sql.getProduct(productName);
        
            boolean searchResult = false;
            if (p != null) {
                searchResult = this.sql.searchProduct(p);
            }
        
            if (searchResult) {
                System.out.println("Product Found!");
                new Popup().info("Search Result", "Product Found!");
            } else {
                System.out.println("Product Not Found!");
                // Assuming Popup class has a warning method
                new Popup().warning("Search Result", "Product Not Found!");
            }
        
            // Assuming updateProductList method updates the product list on the Employee page
            this.g.getCustomerPage().updateProductList(getProducts());
        }
    }
    
    public boolean usernameExists(String username){
        return sql.usernameExists(username);
    }

    public ArrayList<Product> getProducts(){
        return this.sql.getAllProducts(); //Problem with this is that it sorts the list
    }
}
