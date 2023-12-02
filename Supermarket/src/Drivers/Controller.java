package Drivers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Models.*;
import Views.*;

public class Controller implements ActionListener {
    private SQL sql;
    private GUI g;
    private User loggedUser;
    private ArrayList<Product> cart;
    private boolean userSearch = false;
    private Product selectedProduct = null;
    
    public Controller(GUI g){
        this.sql = new SQL();
        this.g = g;
        this.g.setController(this);
        cart = new ArrayList<Product>();
    }

    private void login() {
        // Display a log message indicating the start of the login process
        System.out.println("Login");
    
        // Get the login credentials from the login page
        Login l = this.g.getLoginPage();
    
        // Validate user credentials against the database
        loggedUser = sql.validateUser(l.getUsername(), l.getPassword());
    
        // Check if the user is successfully authenticated
        if (loggedUser != null) {
            // Check the type of the logged-in user and switch to the appropriate page
            if (loggedUser.getType() == 0) {
                // Switch to the employee page if the user is an employee
                this.g.switchToEmployeePage(loggedUser.getUsername());
            } else if (loggedUser.getType() == 1) {
                // Switch to the customer page if the user is a customer
                this.g.switchToCustomerPage(loggedUser.getUsername());
            }
        } else {
            // Display an error message if login fails
            System.out.println("Invalid");
            new Popup().error("Invalid Login", "Invalid Username or Password.");
    
            // Reset the loggedUser variable to null
            loggedUser = null;
        }
    }
    

   // Method to handle user signup
   private void signup() {
    // Prompt the user to enter new user details using a popup
    User u = new Popup().newUser(this);

    // Check if the user provided valid signup information
    if (u != null && !u.getUsername().isEmpty() && !u.getPassword().isEmpty()) {
        // Check if the username already exists in the database
        if (!sql.checkUser(u.getUsername())) { // User does not exist
            // Attempt to add the new user to the database
            if (sql.addUser(u)) {
                // Show a success message if the user is added successfully
                new Popup().info("New User Account", "User Successfully Added!");
            } else {
                // Show a warning message if the user addition fails
                new Popup().warning("New User Account", "User Not Added!");
            }
        } else { // User already exists
            // Show a warning message if the chosen username is already in use
            new Popup().warning("New User Account", "Username is already in use, please try another username.");
        }
    } else {
        // Show a warning message if the user did not provide valid information
        new Popup().warning("New User Account", "Please enter valid username and password.");
    }
}


// Method to handle user logout
private void logout() {
    // Switch to the login page
    this.g.switchToLogin();

    // Reset user-related variables and data structures
    loggedUser = null;
    cart.clear(); // Clear the shopping cart
    selectedProduct = null; // Reset selected product
    userSearch = false; // Reset user search flag
}


    private void addProduct() {
    Employee employee = this.g.getEmployeePage();
    String productName = employee.getProductName();
    double productPrice = employee.getProductPrice();
    int productQuantity = employee.getProductQuantity();

    // Check if required fields are not empty
    if (productName.isEmpty() || productPrice <= 0 || productQuantity <= 0) {
        new Popup().error("Add Product", "Please fill in all fields and ensure values are valid.");
        return; // Exit the function if input is invalid
    }

    if (sql.addProduct(new Product(productName, productPrice, productQuantity))) {
        // Show success message
        JOptionPane.showMessageDialog(null, "New Product Added!", "Add Product", JOptionPane.INFORMATION_MESSAGE);
    } else {
        // Show error message
        JOptionPane.showMessageDialog(null, "Failed to add new product.", "Add Product", JOptionPane.ERROR_MESSAGE);
    }

    // Update the product list
    this.g.getEmployeePage().updateProductList(sql.getProducts()); // SQL: Get Products from Products List
}


   // Method to handle editing a product
private void editProduct() {
    // Display a log message indicating the start of product editing
    System.out.println("Edit Product");

    // Get the selected product based on the product name
    selectedProduct = sql.getProduct(this.g.getEmployeePage().getProductName());

    // Check if the selected product exists
    if (selectedProduct == null) {
        // Show a warning message if the selected product doesn't exist
        new Popup().warning("Search Item", "No item: \"" + this.g.getEmployeePage().getProductName() + "\"");
    } else {
        // Update the quantity and price of the selected product based on user input
        selectedProduct.updateQuantity(this.g.getEmployeePage().getProductQuantity());
        selectedProduct.updatePrice(this.g.getEmployeePage().getProductPrice());

        // Prompt the user if they want to change the absolute quantity
        boolean absolute = false;
        if (new Popup().yesno("Edit Product", "Change Absolute Quantity?")) {
            absolute = true;
        }

        // Edit the product in the database and show appropriate messages
        if (sql.editProduct(selectedProduct, absolute)) {
            new Popup().info("Edit Product", "Product Edited!");
        } else {
            new Popup().error("Edit Product", "Product Not Edited!");
        }
    }

    // Update the product list on the employee page
    this.g.getEmployeePage().updateProductList(sql.getProducts()); // SQL: Get Products from Products List
}

private void removeProduct() {
    // Display a log message indicating the start of product removal
    System.out.println("Remove Stock");

    // Check if a product name is selected
    String productNameToRemove = this.g.getEmployeePage().getProductName();
    if (productNameToRemove == null || productNameToRemove.isEmpty()) {
        // Show a warning message if no product name is selected
        new Popup().warning("Remove Product", "Please select a product to remove.");
        return; // Exit the function if no product name is selected
    }

    // Get the selected product based on the product name
    selectedProduct = sql.getProduct(productNameToRemove);

    // Check if the selected product exists
    if (selectedProduct == null) {
        // Show a warning message if the selected product does not exist
        new Popup().warning("Remove Product", "The selected product does not exist.");
        return; // Exit the function if the selected product does not exist
    }

    // Remove the selected product from the database and show appropriate messages
    if (sql.removeProduct(selectedProduct.getProdID())) {
        System.out.println("Product Removed!");
        new Popup().info("Remove Product", "Product Removed!");
    } else {
        System.out.println("Product Not Removed!");
        new Popup().warning("Remove Product", "Product Not Removed!");
    }

    // Update the product list on the employee page
    this.g.getEmployeePage().updateProductList(sql.getProducts()); // SQL: Get Products from Products List
    selectedProduct = null;
}


// Method to handle adding a product to the shopping cart
private void addToCart() {
    // Display a log message indicating the start of adding to the cart
    System.out.println("Add Cart");

    // Get the product name entered by the user
    String productName = this.g.getCustomerPage().getProductName();

    // Check if the product name is empty
    if (productName.isEmpty()) {
        // Show a warning message if the user hasn't entered a product name
        new Popup().warning("Add to Cart", "Please enter a product name!");
        return;  // Exit the method to prevent further execution
    }

    // Get the selected product based on the product name
    this.selectedProduct = sql.getProduct(productName);

    // Check if the selected product exists
    if (this.selectedProduct == null) {
        // Show a warning message if the selected product doesn't exist
        new Popup().warning("Add to Cart", "Product Does Not Exist!");
        return;  // Exit the method to prevent further execution
    }

    // Set product information on the customer page
    this.g.getCustomerPage().setProductInfo(this.selectedProduct);

    // Get the quantity entered by the user
    int enteredQuantity = this.g.getCustomerPage().getProductQuantity();

    // Check if the entered quantity is valid
    if (enteredQuantity <= 0) {
        // Show a warning message if the user enters an invalid quantity
        new Popup().warning("Add to Cart", "Invalid quantity. Please enter a valid quantity!");
        return;  // Exit the method to prevent further execution
    }

    // Check if the selected product quantity is sufficient
    if (selectedProduct.getQuantity() < enteredQuantity) {
        // Show a warning message if the selected product quantity is insufficient
        new Popup().warning("Add to Cart", "Insufficient stock. Available quantity: " + selectedProduct.getQuantity());
    } else {
        // Update the quantity of the selected product based on user input
        selectedProduct.updateQuantity(enteredQuantity);

        // Add the selected product to the cart
        cart.add(selectedProduct);

        // Show a success message
        new Popup().info("Add to Cart", "Product added to the cart successfully!");
    }

    // Update the cart list on the customer page
    this.g.getCustomerPage().updateCartList(cart);
}

   // Method to handle removing a product from the shopping cart
private void removeFromCart() {
    // Display a log message indicating the start of removing from the cart
    System.out.println("Remove From Cart");

    // Check if the selected product is null
    if (this.selectedProduct == null) {
        // Get the selected product based on the product name from the customer page
        this.selectedProduct = sql.getProduct(this.g.getCustomerPage().getProductName());

        // Check if the selected product exists
        if (this.selectedProduct == null) {
            // Show a warning message if the selected product doesn't exist
            new Popup().warning("Add to Cart", "Product Does Not Exist!");
        } else {
            // Remove the selected product from the cart
            cart.remove(selectedProduct);
        }
    } else {
        // Remove the selected product from the cart
        cart.remove(selectedProduct);
    }

    // Update the cart list on the customer page
    this.g.getCustomerPage().updateCartList(cart);
}

// Method to handle the checkout process
private void checkout() {
    // Display a log message indicating the start of the checkout process
    System.out.println("Checkout");

    // Check if the cart is empty
    if (cart.isEmpty()) {
        // Show a warning message if the cart is empty
        new Popup().warning("Checkout", "Your cart is empty. Please add products before checking out!");
        return;  // Exit the method to prevent further execution
    }

    // Create a new transaction with user, cart, and donation information
    Transaction t = new Transaction(loggedUser, cart, new Popup().donation());

    // Add the transaction to the database
    sql.addTransaction(t);

    // Deduct the purchased products from the database
    for (Product product : cart) {
        sql.deductProduct(product);
    }

    // Clear the cart and update the customer page
    cart.clear();
    this.g.getCustomerPage().clearCart();

    // Update the product list on the customer page
    this.g.getCustomerPage().updateProductList(sql.getProducts()); // SQL: Get Products from Products List

    // Display a confirmation pop-up
    new Popup().info("Checkout Successful", "Thank you for your purchase! Your order has been successfully processed.");
}


// Method to handle searching for a product by the customer
private void searchCustomerProduct() {
    // Display a log message indicating the start of searching for a product by the customer
    System.out.println("Search Product (Customer)");

    // Get the product name from the customer page
    Customer customer = this.g.getCustomerPage();
    String productName = customer.getProductName();

    // Get the selected product based on the product name
    this.selectedProduct = sql.getProduct(productName);

    // Check if the selected product exists
    if (this.selectedProduct != null) {
        System.out.println("Product Found!");

        // Set product information on the customer page
        customer.setProductInfo(this.selectedProduct);

        // Show a success message if the product is found
        new Popup().info("Search Result", "Product Found!");
    } else {
        System.out.println("Product Not Found!");

        // Show a warning message if the product is not found
        new Popup().warning("Search Result", "Product Not Found!");
    }

    // Update the product list on the customer page
    customer.updateProductList(sql.getProducts()); // SQL: Get Products from Products List
}


   // Method to handle searching for a product by an employee
private void searchEmployeeProduct() {
    // Display a log message indicating the start of searching for a product by an employee
    System.out.println("Search Product (Employee)");

    // Get the product name from the employee page
    Employee employee = this.g.getEmployeePage();
    this.selectedProduct = sql.getProduct(employee.getProductName());

    // Check if the selected product exists
    if (this.selectedProduct != null) {
        System.out.println("Product Found!");

        // Set product information on the employee page
        employee.setProductInfo(this.selectedProduct);

        // Show a success message if the product is found
        new Popup().info("Search Result", "Product Found!");
    } else {
        System.out.println("Product Not Found!");

        // Show a warning message if the product is not found
        new Popup().warning("Search Result", "Product Not Found!");
    }

    // Update the product list on the employee page
    employee.updateProductList(sql.getProducts()); // SQL: Get Products from Products List
}

// Utility method to check if a string represents an integer
private boolean isInt(String input) {
    try {
        Integer.parseInt(input);
        return true;
    } catch (Exception e) {
        return false;
    }
}

// Method to handle searching for sales data
private void searchSales() {
    // Clear existing sales data and sales list on the sales page
    g.getSalesPage().clearSalesData();
    g.getSalesPage().clearSalesList();

    // Check if the sales page is in "User" or "Product" search mode
    if (g.getSalesPage().getModeState()) {
        // Search sales data by user
        System.out.println("Search Sales Mode: User");
        User u = null;
        if (isInt(g.getSalesPage().getSearchName()))
            u = sql.getUser(Integer.parseInt(g.getSalesPage().getSearchName()));
        else
            u = sql.getUser(g.getSalesPage().getSearchName());

        // Check if the user exists
        if (u == null) {
            new Popup().warning("Search Sales: User", "Username/UserID does not exist");
        } else {
            // Get transactions by user and update the sales list on the sales page
            ArrayList<Transaction> ts = sql.getTransactionsByUser(u.getUserID());
            if (ts == null)
                new Popup().info("Search Sales: User", "No Sales Found for User");
            else
                this.g.getSalesPage().updateSalesList(ts);
        }
    } else {
        // Search sales data by product
        System.out.println("Search Sales Mode: Product");
        String input = g.getSalesPage().getSearchName();
        ArrayList<Product> sales = new ArrayList<Product>();
        
        if (input == null || input.isEmpty()) {
            new Popup().warning("Search Sales: Product", "Product is not provided");
        } else {
            if (isInt(input)) {
                sales = sql.getSales(Integer.parseInt(input));
            } else {
                sales = sql.getSales(input);
            }
        }
        
        // Check if sales data for the product is found
        if (sales.size() > 0) {
            int totalQuantity = 0;
            double totalSubtotal = 0;

            // Calculate total quantity and subtotal from the sales data
            for (int i = 0; i < sales.size(); i++) {
                totalQuantity += sales.get(i).getQuantity();
                totalSubtotal += sales.get(i).getSubtotal();
            }

            // Update the sales data on the sales page
            this.g.getSalesPage().updateSalesData(totalQuantity, totalSubtotal);
        } else {
            new Popup().warning("Search Sales: Product", "No Sales Found for Product");
        }
    }
}

// Method to switch to the sales page
private void switchToSales() {
    // Switch to the sales page with the username of the logged-in user
    this.g.switchToSalesPage(this.loggedUser.getUsername());
}

// Method to switch to the employee page (stocks)
private void switchToStocks() {
    // Switch to the employee page with the username of the logged-in user
    this.g.switchToEmployeePage(this.loggedUser.getUsername());
}


    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        //Login/Signup Commands
        if(act.equals("loginCommand"))
			login();
        if(act.equals("exitCommand"))
			System.exit(0);
        if(act.equals("signupCommand"))
            signup();
        if(act.equals("logoutCommand"))
			logout();

        //Supermarket Employee Commands
        if(act.equals("addStockCommand"))
			addProduct();
        if(act.equals("editStockCommand"))
            editProduct();
        if (act.equals("removeStockCommand"))
            removeProduct();
        if (act.equals("searchEmployeeProductCommand"))
            searchEmployeeProduct();
        if (act.equals("searchSalesCommand"))
            searchSales();
        if (act.equals("switchToSalesCommand"))
            switchToSales();
        if (act.equals("switchToStocksCommand"))
            switchToStocks();
    
        //Supermarket Client Commands
        if(act.equals("addToCartCommand"))
            addToCart();
        if(act.equals("removeFromCartCommand"))
            removeFromCart();
        if (act.equals("checkoutCommand"))
            checkout();
        if (act.equals("searchCustomerProductCommand"))
            searchCustomerProduct();
    }

    public boolean usernameExists(String username){
        return false;
    }

    public ArrayList<Product> getProducts(){
        return sql.getProducts();
    }
}
