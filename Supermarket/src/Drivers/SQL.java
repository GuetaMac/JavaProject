package Drivers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void testConnection(){
        System.out.println("Testing SQL Connection...");
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);){
            System.out.println("SQL Connection Success!");
        } catch (SQLException e) {
            System.out.println("SQL Connection Failed!");
        } 
    }

    public ArrayList<Product> generateProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Mismo Coke", 20.00, 10));
        //... Continue these
        products.add(new Product("Mountain Dew", 20.0, 15));
        products.add(new Product("Royal", 20.0, 5));
        products.add(new Product("Sprite", 20.0, 12));
        products.add(new Product("Piattos", 17.0, 8));
        products.add(new Product("Nova", 17.0, 20));
        return products;
    }

    public ArrayList<User> generateUsers(){
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("admin", "admin", 0, "Administrator", "Manila"));
        users.add(new User("juan", "juan", 1, "Juan Dela Cruz", "Manila, Philippines"));
        return users;
    }
    
    public ArrayList<Product> getAllProducts(){
        String sampleReadDB = "SELECT * from products"; //Custom Command
        ArrayList<Product> products = new ArrayList<>();
        try(
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
                Statement stmt = conn.createStatement(); //<== For permanent sql commands
                ResultSet rs = stmt.executeQuery(sampleReadDB)) {	//ResultSet if may output or return value from db  
            System.out.println("Successfully Connected to DB");
            while (rs.next()) {   
                products.add(new Product(rs.getString("prodname"), rs.getDouble("price"),rs.getInt("Stocks")));
            }
        } catch (Exception e) {
           //e.printStackTrace();
        }
        return products;
    }

    public ArrayList<User> getAllUsers(){
        String sampleReadDB = "SELECT * from user"; //Custom Command
        ArrayList<User> users = new ArrayList<>();
        try(
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
            Statement stmt = conn.createStatement(); //<== For permanent sql commands
            ResultSet rs = stmt.executeQuery(sampleReadDB)) {	//ResultSet if may output or return value from db  
            System.out.println("Successfully Connected to DB");
            while (rs.next()) {   
                users.add(new User(rs.getString("username"), rs.getString("password"), rs.getInt("type"), rs.getString("name"), rs.getString("address")));
            }
        } catch (Exception e) {
           //e.printStackTrace();
        }
        return users;
    }

    public Product getProduct(String string){
        string = string.strip();
        String sampleReadDB = "SELECT * from products where prodname=?"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {	//ResultSet if may output or return value from db  
            System.out.println("Successfully Connected to DB");
            PreparedStatement pstmt = conn.prepareStatement(sampleReadDB); //<== For permanent sql commands
            pstmt.setString(1, string);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {   
                return new Product(rs.getString("prodname"), rs.getDouble("price"), rs.getInt("stocks")); //either 0/1
            }
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
        return null; //invalid or error
    }

    public User getUser(String username, String password){
        username = username.replace(" ", "");
        password = password.replace(" ", "");
        String sampleReadDB = "SELECT * from user where username=? and password=?"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {	//ResultSet if may output or return value from db  
            System.out.println("Successfully Connected to DB");
            PreparedStatement pstmt = conn.prepareStatement(sampleReadDB); //<== For permanent sql commands
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {   
                return new User(rs.getString("username"), rs.getString("password"), rs.getInt("type"), rs.getString("name"), rs.getString("address")); //either 0/1
            }
        } catch (Exception e) {
           //e.printStackTrace();
           return null;
        }
        return null; //invalid or error
    }

    public boolean usernameExists(String username){
        username = username.strip();
        ArrayList<User> users = getAllUsers();
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }

    public void buildDB(){
        String sampleWriteDB = "CREATE Database Grocery"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL_INIT, USER, PASS);) {	  
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sampleWriteDB);
            int result = pstmt.executeUpdate();
            if(result != 0){
                System.out.println("DB Made!");
            }else{
                System.out.println("DB Not Made!");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void buildUserTable(){
        //every user has a userID that autoincrements as well as a username that is unique
        String buildTable = "CREATE table user(userID int(6) auto_increment primary key, username char(20) unique, password char(20), type int(1), name char(50), address char(100))"; //Custom Command
        int result = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);){
                PreparedStatement pstmt = conn.prepareStatement(buildTable); //<== Useful if may specific query (i.e., yung ?); Although pwede rin sa permanent commands.
                result = pstmt.executeUpdate();
                if(result != 0){
                    System.out.println("Product Table Not Added!");
                }else{
                    System.out.println("Product Table Added!");
                }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void buildProductTable(){
        String buildTable = "CREATE table products(prodID int(6) auto_increment primary key, prodname char(20), price decimal(6,2), Stocks int(5))"; //Custom Command
        int result = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);){
                PreparedStatement pstmt = conn.prepareStatement(buildTable); //<== Useful if may specific query (i.e., yung ?); Although pwede rin sa permanent commands.
                result = pstmt.executeUpdate();
                if(result != 0){
                    System.out.println("Product Table Not Added!");
                }else{
                    System.out.println("Product Table Added!");
                }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    
    public void buildTransactionTable(){
        //every transaction has a unique transactID that is autoincrementing and is the primary key
        String buildTransaction = "CREATE table transactions(transactID int(6) not null auto_increment, buyer char(20), subtotal double(99,2), items int(5), prodname char(20), orderdatetime timestamp, PRIMARY KEY (transactID));"; //Custom Command
        int result = 0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);){
                PreparedStatement pstmt = conn.prepareStatement(buildTransaction);
                result = pstmt.executeUpdate();
                if(result != 0){
                    System.out.println("Transaction Table Not Added!");
                }else{
                    System.out.println("Transaction Table Added!");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private boolean addProduct(String prodname, double price, int stocks){
        String sampleWriteDB = "INSERT INTO products(Prodname, price, stocks) VALUES(?,?,?)"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {	  
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sampleWriteDB);
            pstmt.setString(1, prodname);
            pstmt.setString(2, (price + ""));
            pstmt.setString(3, (stocks + ""));
            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("Product Not Added!");
                return false;
            }else{
                System.out.println("Product Added!");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean editProduct(String prodname, double price, int quantity){
        //UPDATE table_name SET column_name1= value1, column_name2= value2 WHERE condition;
        String sampleWriteDB = "UPDATE products set stocks=?, price=? WHERE prodname=?"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {	  
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sampleWriteDB);
            pstmt.setInt(1, quantity);
            pstmt.setDouble(2, price);
            pstmt.setString(3,prodname);
            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("Product Not Edited!");
                return false;
            }else{
                System.out.println("Product Edited!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }  
    
    public boolean removeProduct(String prodname) {
        String sampleWriteDB = "DELETE FROM products WHERE Prodname=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement pstmt = conn.prepareStatement(sampleWriteDB);
            pstmt.setString(1, prodname);
            int result = pstmt.executeUpdate();
            return result > 0; // Returns true if at least one row was deleted
        } catch (Exception e) {
            // Handle exceptions
            return false; // Return false in case of an exception
        }
    }

    public boolean removeProduct(Product p){
        return removeProduct(p.getName());
    }

    public boolean editProduct(Product p){
        return editProduct(p.getName(), p.getPrice(), p.getQuantity());
    }

    public boolean deductProduct(Product p, int deductQuantity){
        //This version of code is a basic one. It assumes no concurrency in database operations which is a whole different topic beyond OOP.
        Product reference = getProduct(p.getName());
        return editProduct(reference.getName(), reference.getPrice(), reference.getQuantity()-deductQuantity);
    }

    public boolean addProduct(Product p){
        return addProduct(p.getName(), p.getPrice(), p.getQuantity());
    }
    
    private boolean addUser(String username, String password, int type, String name, String address){
        String sampleWriteDB = "INSERT INTO user(username, password, type, name, address) VALUES(?,?,?,?,?)"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {	  
            //PREPARED STATEMENT EXAMPLE
            PreparedStatement pstmt = conn.prepareStatement(sampleWriteDB);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, (type+""));
            pstmt.setString(4, name);
            pstmt.setString(5, address);
            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("User Not Added!");
                return false;
            }else{
                System.out.println("User Added!");
                return true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public boolean addUser(User u){
        return addUser(u.getUsername(), u.getPassword(), u.getType(), u.getName(), u.getAddress());
    }
    

    private boolean searchProduct(String prodname) {
        String sampleReadDB = "SELECT * FROM products WHERE prodname=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement pstmt = conn.prepareStatement(sampleReadDB);
            pstmt.setString(1, prodname);
            ResultSet resultSet = pstmt.executeQuery();

    
            // For simplicity, let's just return true if any result is found
            return resultSet.next();
        } catch (Exception e) {
            // Handle exceptions (e.g., SQLException)
            //e.printStackTrace(); // Print the exception details for debugging purposes
            return false; // Return false in case of an exception
        }
    }

    public boolean searchProduct(Product p){
        return searchProduct(p.getName());
    }
    
    public void addTransaction(String buyer, double subtotal, int quantity, String prodname){
        //Note that this does not list the specific items of
        System.out.println("Add Transaction...");
        String sampleWriteDB = "INSERT INTO transactions(buyer, subtotal, items, prodname, OrderDateTime) VALUES(?,?,?,?,?)"; //Custom Command
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {	  
            //PREPARED STATEMENT EXAMPLE
            System.out.println("Preparing Statement...");
            PreparedStatement pstmt = conn.prepareStatement(sampleWriteDB);
            pstmt.setString(1, buyer);
            pstmt.setDouble(2, subtotal);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, prodname);
            pstmt.setTimestamp(5, new java.sql.Timestamp(java.util.Calendar.getInstance().getTimeInMillis()));
            System.out.println("Executing Statement...");
            if(pstmt.executeUpdate() == 0){
                System.out.println("Transaction Not Added!");
            }else{
                System.out.println("Transaction Added!");
            }
            System.out.println("Statement Executed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //0 = employee, 1 = client, -1 = invalid;
    //It is okay not to convert it to user as it only contains 2 values
    public int checkCredential(String username, String password){
        User u = getUser(username, password);
        if (u == null)
            return -1;
        else
            return u.getType();
    }
}
