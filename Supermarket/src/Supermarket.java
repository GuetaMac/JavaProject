import java.util.ArrayList;

import Drivers.Controller;
import Drivers.GUI;
import Drivers.SQL;
import Models.*;

public class Supermarket {
    public static void main(String[] args) {
        //Building default SQL server data
        if(false){
            SQL sql = new SQL();
            sql.buildDB();
            sql.buildUsersTable();
            sql.buildProductsTable();
            sql.buildTransactionsTable();
            sql.buildTransactionItemTable();

            sql.addUser(new User("admin", "admin", 0, "Test Admin", "Philippines"));
            sql.addUser(new User("user", "user", 1, "Test Client", "Philippines"));
            ArrayList<User> users = sql.getUsers();
            for(int i = 0; i < users.size(); i++){
                users.get(i).printSummary();
            }
            System.out.println("");
            
            sql.addProduct(new Product("Piattos", 20.00, 100));
            sql.addProduct(new Product("Nova", 20.00, 100));
            sql.addProduct(new Product("Clover", 8.00, 100));
            sql.addProduct(new Product("Mismo Royal", 20.00, 100));
            sql.addProduct(new Product("Mismo Coke", 20.00, 100));
            sql.addProduct(new Product("Loaded (Vanilla)", 8.00, 100));
            sql.addProduct(new Product("Moby", 8.00, 100));
            ArrayList<Product> p = sql.getProducts();
            for(int i = 0; i < p.size(); i++){
                p.get(i).printSummary();
            }
            System.out.println("");
        }

        GUI g = new GUI();
        Controller c = new Controller(g);

        g.setVisible(true);
        g.switchToLogin();
    }
}