package Drivers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.*;

public class SQL {
    private final String DB_URL_INIT = "jdbc:mysql://localhost/"; //For cases when you build a new DB.
    private final String DB_URL = "jdbc:mysql://localhost/Grocery";
    private final String USER = "root";
    private final String PASS = "";

    public SQL(){
        testConnection();
    }


    // Establishes a private connection to the initial database using the specified URL, username, and password.
    private Connection getInitialConnection(){
        try{
            Connection c = DriverManager.getConnection(DB_URL_INIT, USER, PASS);
            System.out.println("SQL Connection Success!");
            return c;
        } catch (SQLException e){
            System.out.println("SQL Connection Failed!");
            System.out.println(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    // Establishes a private connection to the main database using the specified URL, username, and password.
    private Connection getConnection(){
        try{
            Connection c = DriverManager.getConnection(DB_URL, USER, PASS);
            return c;
        } catch (SQLException e){
            System.out.println("SQL Connection Failed!");
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Method to test the SQL connection by attempting to establish the initial connection.
    public void testConnection(){
        System.out.println("Testing SQL Connection...");
        getInitialConnection();
    }

    // Method to create a new database named "Grocery" using a custom SQL command.
    public void buildDB(){
        String sql = "CREATE DATABASE Grocery"; // Custom SQL command to create a new database named "Grocery"
        Connection c = getInitialConnection(); // Establishes the initial connection to perform the database creation.
        try{
            PreparedStatement pstmt = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            if(pstmt.executeUpdate() != 0){
                System.out.println("DB Made!"); // Indicates successful database creation.
            }else{
                System.out.println("DB Not Made!"); // Indicates failure in database creation.
            }
            c.close(); // Closes the connection after completing the database creation.
        } catch(SQLException e){
            System.out.println("SQLException occurred on buildDB()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }   
    }

    // Method to create the "users" table in the database with specified columns and constraints.
    public void buildUsersTable(){
        String[] sql = new String[3];
        sql[0] = "CREATE TABLE users (userID int(11) NOT NULL, username varchar(50) NOT NULL UNIQUE, password varchar(50) NOT NULL, name varchar(100) NOT NULL, address varchar(200) NOT NULL, type INT NOT NULL);";
        sql[1] = "ALTER TABLE users ADD PRIMARY KEY (userID), ADD KEY userID (userID);"; // Adds a primary key to the "users" table.
        sql[2] = "ALTER TABLE users MODIFY userID int(11) NOT NULL AUTO_INCREMENT UNIQUE;"; // Modifies the userID column to be an auto-incrementing primary key.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            for(int i = 0; i < sql.length; i++){
                PreparedStatement pstmt = c.prepareStatement(sql[i]); // Prepares the SQL statements for execution.
                if(pstmt.executeUpdate() == 0)
                    System.out.println("Users Table Made!"); // Indicates successful creation of the "users" table.
                else
                    System.out.println("Users Table Not Made!"); // Indicates failure in creating the "users" table.
            }
            
        } catch(SQLException e){
            System.out.println("SQLException occurred on buildUsersTable()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
    }

    // Method to create the "products" table in the database with specified columns and constraints.
    public void buildProductsTable(){
        String[] sql = new String[3];
        sql[0] = "CREATE TABLE products (prodID int(11) NOT NULL, name varchar(100) NOT NULL UNIQUE, price decimal(10,2) NOT NULL, quantity int(11) NOT NULL);";
        sql[1] = "ALTER TABLE products ADD PRIMARY KEY (prodID);"; // Adds a primary key to the "products" table.
        sql[2] = "ALTER TABLE products MODIFY prodID int(11) NOT NULL AUTO_INCREMENT UNIQUE;"; // Modifies the prodID column to be an auto-incrementing primary key.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            for(int i = 0; i < sql.length; i++){
                PreparedStatement pstmt = c.prepareStatement(sql[i]); // Prepares the SQL statements for execution.
                if(pstmt.executeUpdate() == 0)
                    System.out.println("Products Table Made!"); // Indicates successful creation of the "products" table.
                else
                    System.out.println("Products Table Not Made!"); // Indicates failure in creating the "products" table.
            }
            
        } catch(SQLException e){
            System.out.println("SQLException occurred on buildProductsTable()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
    }

    // Method to create the "transactions" table in the database with specified columns and constraints.
    public void buildTransactionsTable(){
        String[] sql = new String[4];
        sql[0] = "CREATE TABLE transactions (transactID int(11) NOT NULL, userID int(11) NOT NULL, subtotal decimal(10,0) NOT NULL, quantity int(11) NOT NULL, orderdatetime timestamp NOT NULL DEFAULT current_timestamp(), donation decimal(10,0));";
        sql[1] = "ALTER TABLE transactions ADD PRIMARY KEY (transactID), ADD KEY userID (userID);"; // Adds a primary key and a foreign key to the "transactions" table.
        sql[2] = "ALTER TABLE transactions ADD CONSTRAINT transactions_ibfk_1 FOREIGN KEY (userID) REFERENCES users (userID);"; // Adds a foreign key constraint to link "transactions" and "users" tables.
        sql[3] = "ALTER TABLE transactions MODIFY transactID int(11) NOT NULL AUTO_INCREMENT UNIQUE;"; // Modifies the transactID column to be an auto-incrementing primary key.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            for(int i = 0; i < sql.length; i++){
                PreparedStatement pstmt = c.prepareStatement(sql[i]); // Prepares the SQL statements for execution.
                if(pstmt.executeUpdate() == 0)
                    System.out.println("Transactions Table Made!"); // Indicates successful creation of the "transactions" table.
                else
                    System.out.println("Transactions Table Not Made!"); // Indicates failure in creating the "transactions" table.
            }
            
        } catch(SQLException e){
            System.out.println("SQLException occurred on buildTransactionsTable()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
    }

    // Method to create the "transaction_item" table in the database with specified columns and constraints.
    public void buildTransactionItemTable(){
        String[] sql = new String[3];
        sql[0] = "CREATE TABLE transaction_item (transactID int(11) NOT NULL, prodID int(11) NOT NULL, quantity int(11) NOT NULL, subtotal decimal(10,0) NOT NULL);";
        sql[1] = "ALTER TABLE transaction_item ADD KEY transactID (transactID,prodID), ADD KEY prodID (prodID);"; // Adds indexes to optimize query performance.
        sql[2] = "ALTER TABLE transaction_item ADD CONSTRAINT transaction_item_ibfk_1 FOREIGN KEY (prodID) REFERENCES products (prodID), ADD CONSTRAINT transaction_item_ibfk_2 FOREIGN KEY (transactID) REFERENCES transactions (transactID);"; // Adds foreign key constraints to link "transaction_item," "products," and "transactions" tables.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            for(int i = 0; i < sql.length; i++){
                PreparedStatement pstmt = c.prepareStatement(sql[i]); // Prepares the SQL statements for execution.
                if(pstmt.executeUpdate() == 0)
                    System.out.println("Transaction Items Table Made!"); // Indicates successful creation of the "transaction_item" table.
                else
                    System.out.println("Transaction Items Table Not Made!"); // Indicates failure in creating the "transaction_item" table.
            }
            
        } catch(SQLException e){
            System.out.println("SQLException occurred on buildTransactionItemTable()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
    }

    // Method to add a product to the "products" table in the database.
    public boolean addProduct(Product p){
        String sql = "INSERT INTO products(name, price, quantity) VALUES (?,?,?);"; // SQL statement to insert product data into the "products" table.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setString(1, p.getName()); // Sets the product name in the SQL statement.
            pStatement.setDouble(2, p.getPrice()); // Sets the product price in the SQL statement.
            pStatement.setInt(3, p.getQuantity()); // Sets the product quantity in the SQL statement.
            return !pStatement.execute(); // Executes the SQL statement and returns true if successful, false otherwise.
        } catch(SQLException e){
            System.out.println("SQLException occurred on addProduct()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
        return false; // Returns false in case of an exception or unsuccessful execution.
    }

    // Method to add a user to the "users" table in the database.
    public boolean addUser(User u){
        String sql = "INSERT INTO users(username, password, name, address, type) VALUES (?,?,?,?,?);"; // SQL statement to insert user data into the "users" table.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setString(1, u.getUsername()); // Sets the username in the SQL statement.
            pStatement.setString(2, u.getPassword()); // Sets the password in the SQL statement.
            pStatement.setString(3, u.getName()); // Sets the user's name in the SQL statement.
            pStatement.setString(4, u.getAddress()); // Sets the user's address in the SQL statement.
            pStatement.setInt(5, u.getType()); // Sets the user type in the SQL statement.
            return !pStatement.execute(); // Executes the SQL statement and returns true if successful, false otherwise.
        } catch(SQLException e){
            System.out.println("SQLException occurred on addUser()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
        return false; // Returns false in case of an exception or unsuccessful execution.
    }

    // Method to add a transaction to the database, including both overall transaction details and individual items.
    public void addTransaction(Transaction t){
        double subtotal = 0; // Variable to store the overall subtotal of the transaction.
        int quantity = 0; // Variable to store the overall quantity of items in the transaction.
        int transactID = 0; // Variable to store the generated transaction ID.
        
        // Calculating overall subtotal and quantity from individual items in the transaction.
        for(int i = 0; i < t.getItems().size(); i++){
            subtotal += t.getItems().get(i).getSubtotal();
            quantity += t.getItems().get(i).getQuantity();
        }

        // SQL statement to insert overall transaction details into the "transactions" table.
        String sql = "INSERT INTO transactions(userID, subtotal, quantity, donation) VALUES (?,?,?,?);";
        Connection c = getConnection(); // Establishes a connection to the database.
        
        try{
            // Prepares the SQL statement for execution, specifying that the "transactID" should be returned.
            PreparedStatement pStatement = c.prepareStatement(sql, new String[] {"transactID"});
            pStatement.setInt(1, t.getBuyer().getUserID());
            pStatement.setDouble(2, subtotal);
            pStatement.setInt(3, quantity);
            pStatement.setDouble(4, t.getDonation());
            pStatement.executeUpdate(); // Executes the SQL statement to insert overall transaction details.
            
            // Retrieves the generated "transactID" from the executed SQL statement.
            ResultSet rs = pStatement.getGeneratedKeys();
            while (rs.next())
                transactID = rs.getInt(1);
        } catch(SQLException e){
            System.out.println("SQLException occurred on addTransaction() Overall!");
            System.out.println(e.getMessage());
        }
        
        // SQL statement to insert individual items into the "transaction_item" table.
        sql = "INSERT INTO transaction_item(transactID, prodID, quantity, subtotal) VALUES(?,?,?,?);";
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, transactID); // Sets the "transactID" for the individual items.
            
            // Iterates through each item in the transaction and inserts it into the "transaction_item" table.
            for(int i = 0; i < t.getItems().size(); i++){
                pStatement.setInt(2, t.getItems().get(i).getProdID());
                pStatement.setInt(3, t.getItems().get(i).getQuantity());
                pStatement.setDouble(4, t.getItems().get(i).getSubtotal());
                pStatement.execute(); // Executes the SQL statement to insert an individual item.
            } 
        } catch(SQLException e){
            System.out.println("SQLException occurred on addTransaction() Individual!");
            System.out.println(e.getMessage());
        }
    }

    // Method to edit a product in the "products" table, allowing for either absolute or relative changes to the quantity.
    public boolean editProduct(Product p, boolean absolute){
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE prodID = ?;"; // SQL statement to update product details.
        Connection c = getConnection(); // Establishes a connection to the database.
        
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setString(1, p.getName()); // Sets the new product name.
            pStatement.setDouble(2, p.getPrice()); // Sets the new product price.
            
            // Checks if the change to the quantity is absolute or relative to the current quantity.
            if(!absolute)
                pStatement.setInt(3, p.getQuantity() + getProduct(p.getProdID()).getQuantity());
            else
                pStatement.setInt(3, p.getQuantity());
            
            pStatement.setInt(4, p.getProdID()); // Sets the product ID for the update.
            int r = pStatement.executeUpdate(); // Executes the SQL statement and gets the number of affected rows.
            
            if(r > 0)
                return true; // Returns true if the update was successful.
        } catch(SQLException e){
            System.out.println("SQLException occurred on editProduct()!"); // Handles any SQL exceptions that may occur.
            System.out.println(e.getMessage());
        }
        return false; // Returns false if the update was unsuccessful.
    }


    // Method to deduct a specified quantity of a product from the "products" table in the database.
    public boolean deductProduct(Product p){
        String sql = "UPDATE products SET quantity = ? WHERE prodID = ?;"; // SQL statement to update the product quantity.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, getProduct(p.getProdID()).getQuantity()-p.getQuantity()); // Calculates the new quantity after deduction.
            pStatement.setInt(2, p.getProdID()); // Sets the product ID for the update.
            int r = pStatement.executeUpdate(); // Executes the SQL statement and gets the number of affected rows.
            if(r > 0)
                return true; // Returns true if the update was successful.
        } catch(SQLException e){
            System.out.println("SQLException occurred on deductProduct()!");
            System.out.println(e.getMessage());
        }
        return false; // Returns false if the update was unsuccessful.
    }

    // Method to remove a product from the "products" table in the database.
    public boolean removeProduct(int prodID){
        String sql = "DELETE FROM products WHERE prodID=?;"; // SQL statement to delete a product.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, prodID); // Sets the product ID for deletion.
            return !pStatement.execute(); // Executes the SQL statement and returns true if successful, false otherwise.
        } catch(SQLException e){
            System.out.println("SQLException occurred on removeProduct()!");
            System.out.println(e.getMessage());
        }
        return false; // Returns false in case of an exception or unsuccessful execution.
    }

    // Method to retrieve a list of all users from the "users" table in the database.
    public ArrayList<User> getUsers(){
        String sql = "SELECT * FROM users"; // SQL statement to select all users from the "users" table.
        ArrayList<User> list = new ArrayList<User>(); // ArrayList to store the retrieved users.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Iterates through the result set and creates User objects for each user in the "users" table.
            while(rSet.next()){
                User u = new User(  rSet.getInt("userID"), rSet.getString("username"), 
                                    rSet.getString("password"), rSet.getInt("type"), 
                                    rSet.getString("name"), rSet.getString("address"));
                list.add(u); // Adds the User object to the ArrayList.
            }
        } catch(SQLException e){
            System.out.println("SQLException occurred on getUsers()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
        return list; // Returns the ArrayList of User objects.
    }


    // Method to retrieve a list of all products from the "products" table in the database.
    public ArrayList<Product> getProducts(){
        String sql = "SELECT * FROM products"; // SQL statement to select all products from the "products" table.
        ArrayList<Product> list = new ArrayList<Product>(); // ArrayList to store the retrieved products.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Iterates through the result set and creates Product objects for each product in the "products" table.
            while(rSet.next()){
                Product p = new Product(rSet.getInt("prodID"), rSet.getString("name"), rSet.getDouble("price"), rSet.getInt("quantity"));
                list.add(p); // Adds the Product object to the ArrayList.
            }
        } catch(SQLException e){
            System.out.println("SQLException occurred on getProducts()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
        return list; // Returns the ArrayList of Product objects.
    }

    // Method to retrieve a product from the "products" table based on the product ID.
    public Product getProduct(int prodID){
        String sql = "SELECT * FROM products WHERE prodID=?"; // SQL statement to select a product by product ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, prodID); // Sets the product ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and returns a Product object for the retrieved product.
            while(rSet.next())
                return new Product(rSet.getInt("prodID"), rSet.getString("name"), rSet.getDouble("price"), rSet.getInt("quantity"));
        } catch(SQLException e){
            System.out.println("SQLException occurred on getProduct()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
        return null; // Returns null if no product is found.
    }

    // Method to retrieve a product from the "products" table based on the product name.
    public Product getProduct(String name){
        String sql = "SELECT * FROM products WHERE name=?"; // SQL statement to select a product by name.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setString(1, name); // Sets the product name parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and returns a Product object for the retrieved product.
            while(rSet.next())
                return new Product(rSet.getInt("prodID"), rSet.getString("name"), rSet.getDouble("price"), rSet.getInt("quantity"));
        } catch(SQLException e){
            System.out.println("SQLException occurred on getProduct()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
        return null; // Returns null if no product is found.
    }

    // Method to retrieve a user from the "users" table based on the username.
    public User getUser(String username){
        String sql = "SELECT * FROM users WHERE username=?"; // SQL statement to select a user by username.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setString(1, username); // Sets the username parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and returns a User object for the retrieved user.
            while(rSet.next())
                return new User(rSet.getInt("userID"), rSet.getString("username"), 
                                rSet.getString("password"), rSet.getInt("type"), 
                                rSet.getString("name"), rSet.getString("address"));
        } catch(SQLException e){
            System.out.println("SQLException occurred on getUser()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
        return null; // Returns null if no user is found.
    }

    // Method to retrieve a user from the "users" table based on the user ID.
    public User getUser(int userID){
        String sql = "SELECT * FROM users WHERE userID=?"; // SQL statement to select a user by user ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, userID); // Sets the user ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and returns a User object for the retrieved user.
            while(rSet.next())
                return new User(rSet.getInt("userID"), rSet.getString("username"), 
                                rSet.getString("password"), rSet.getInt("type"), 
                                rSet.getString("name"), rSet.getString("address"));
        } catch(SQLException e){
            System.out.println("SQLException occurred on getUser()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
        return null; // Returns null if no user is found.
    }

    // Method to get the buyer (User) associated with a specific transaction ID.
    public User getBuyerTransaction(int transactID){
        String sql = "SELECT * FROM transactions WHERE transactID=?;"; // SQL statement to select a transaction by transaction ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, transactID); // Sets the transaction ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Retrieves the user ID associated with the transaction and returns the corresponding User object.
            while(rSet.next()){
                int userID = rSet.getInt("userID");
                System.out.println("User ID: " + userID);
                return getUser(userID);
            }
            return null; // Returns null if no user is found.
        } catch(SQLException e){
            System.out.println("SQLException occurred on getBuyerTransaction()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
    }

    // Method to get a list of transactions associated with a specific user ID.
    public ArrayList<Transaction> getTransactionsByUser(int userID){
        String sql = "SELECT transactID FROM transactions WHERE userID=?;"; // SQL statement to select transaction IDs by user ID.
        String sql2 = "SELECT * FROM transactions WHERE transactID=?;"; // SQL statement to select transactions by transaction ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the first SQL statement for execution.
            pStatement.setInt(1, userID); // Sets the user ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            ArrayList<Integer> transactIDs = new ArrayList<Integer>(); // ArrayList to store transaction IDs.
            
            // Adds transaction IDs to the ArrayList.
            while(rSet.next())
                transactIDs.add(rSet.getInt("transactID"));
            
            // Prepares the second SQL statement for execution.
            pStatement = c.prepareStatement(sql2);
            ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // ArrayList to store transactions.
            
            // Retrieves transactions based on transaction IDs and adds them to the ArrayList.
            for(int i = 0; i < transactIDs.size(); i++){
                pStatement.setInt(1, transactIDs.get(i));
                rSet = pStatement.executeQuery();
                transactions.add(getTransaction(transactIDs.get(i)));
            }
            return transactions; // Returns the ArrayList of transactions.
        } catch(SQLException e){
            System.out.println("SQLException occurred on getTransactionsByUser()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
    }


    // Method to retrieve a specific transaction from the "transactions" table based on transaction ID.
    public Transaction getTransaction(int transactID){
        String sql = "SELECT * FROM transactions WHERE transactID=?;"; // SQL statement to select a transaction by transaction ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, transactID); // Sets the transaction ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and returns a Transaction object for the retrieved transaction.
            while(rSet.next()){
                User u = getUser(rSet.getInt("userID")); // Retrieves the user associated with the transaction.
                ArrayList<Product> items = getTransactItemsByTransactProducts(transactID); // Retrieves transaction items.
                return new Transaction(rSet.getInt("transactID"), u, items, rSet.getTimestamp("orderdatetime"), rSet.getDouble("donation"));
            }
            return null; // Returns null if no transaction is found.
        } catch(SQLException e){
            System.out.println("SQLException occurred on getTransaction()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
    }

    // Method to retrieve a list of products that have been sold based on product ID.
    public ArrayList<Product> getSales(int prodID){
        ArrayList<TransactionItem> ts = getTransactItemsByProduct(prodID); // Retrieves transaction items for a specific product.
        ArrayList<Product> productsOnly = new ArrayList<Product>(); // ArrayList to store the products sold.
        for(int i = 0; i < ts.size(); i++)
            productsOnly.add(ts.get(i).getProduct()); // Adds each product to the ArrayList.
        return productsOnly; // Returns the ArrayList of products sold.
    }

    // Method to retrieve a list of products that have been sold based on product name.
    public ArrayList<Product> getSales(String name){
        Product product = getProduct(name);
        if (product != null) {
            return getSales(product.getProdID());
        } else {
            return new ArrayList<>();
        }
    }
    // Private method to retrieve transaction items for a specific product from the "transaction_item" table.
    private ArrayList<TransactionItem> getTransactItemsByProduct(int prodID){
        String sql = "SELECT * FROM transaction_item WHERE prodID=?;"; // SQL statement to select transaction items by product ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        ArrayList<TransactionItem> ti = new ArrayList<TransactionItem>(); // ArrayList to store transaction items.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, prodID); // Sets the product ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and adds TransactionItem objects to the ArrayList for each retrieved transaction item.
            while(rSet.next()){
                Product p = getProduct(rSet.getInt("prodID")); // Retrieves the product associated with the transaction item.
                ti.add(new TransactionItem(rSet.getInt("transactID"), p, rSet.getInt("quantity"), rSet.getDouble("subtotal"))); // Adds the TransactionItem object to the ArrayList.
            }
            return ti; // Returns the ArrayList of transaction items.
        } catch(SQLException e){
            System.out.println("SQLException occurred on getTransactItemsByTransact()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
    }


    // Private method to retrieve transaction items for a specific transaction ID from the "transaction_item" table.
    private ArrayList<TransactionItem> getTransactItemsByTransact(int transactID){
        String sql = "SELECT * FROM transaction_item WHERE transactID=?;"; // SQL statement to select transaction items by transaction ID.
        Connection c = getConnection(); // Establishes a connection to the database.
        ArrayList<TransactionItem> ti = new ArrayList<TransactionItem>(); // ArrayList to store transaction items.
        try{
            PreparedStatement pStatement = c.prepareStatement(sql); // Prepares the SQL statement for execution.
            pStatement.setInt(1, transactID); // Sets the transaction ID parameter in the SQL statement.
            ResultSet rSet = pStatement.executeQuery(); // Executes the SQL statement and retrieves the result set.
            
            // Creates and adds TransactionItem objects to the ArrayList for each retrieved transaction item.
            while(rSet.next()){
                Product p = getProduct(rSet.getInt("prodID")); // Retrieves the product associated with the transaction item.
                ti.add(new TransactionItem(transactID, p, rSet.getInt("quantity"), rSet.getDouble("subtotal"))); // Adds the TransactionItem object to the ArrayList.
            }
            return ti; // Returns the ArrayList of transaction items.
        } catch(SQLException e){
            System.out.println("SQLException occurred on getTransactItemsByTransact()!");
            System.out.println(e.getMessage());
            return null; // Returns null in case of an exception.
        }
    }

    // Private method to retrieve products for a specific transaction ID by using transaction items.
    private ArrayList<Product> getTransactItemsByTransactProducts(int transactID){
        ArrayList<TransactionItem> ti = getTransactItemsByTransact(transactID); // Retrieves transaction items for a specific transaction.
        ArrayList<Product> productsOnly = new ArrayList<Product>(); // ArrayList to store the products associated with the transaction.
        for(int i = 0; i < ti.size(); i++)
            productsOnly.add(ti.get(i).getProduct()); // Adds each product to the ArrayList.
        return productsOnly; // Returns the ArrayList of products associated with the transaction.
    }

    // Method to validate a user by checking the provided username and password.
    public User validateUser(String username, String password){
        User u = getUser(username); // Retrieves the user based on the provided username.
        if(u != null)
            if(u.getPassword().equals(password))
                return u; // Returns the user if the password matches.
        return null; // Returns null if the user is not found or the password doesn't match.
    }

    // Method to check if a user with the provided username exists.
    public boolean checkUser(String username){
        if(getUser(username) != null)
            return true; // Returns true if a user with the provided username exists.
        return false; // Returns false if no user with the provided username is found.
    }
}
