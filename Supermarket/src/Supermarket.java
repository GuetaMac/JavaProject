import java.util.ArrayList;

import Drivers.Controller;
import Drivers.GUI;
import Drivers.SQL;
import Models.*;
import Views.Popup;

public class Supermarket {
    public static void main(String[] args) {
        //Building default SQL server data
        if(false){
            SQL sql = new SQL();
            sql.buildDB();
            sql.buildProductTable();
            sql.buildUserTable();
            sql.buildTransactionTable();

            //Testing SQL calls
            ArrayList<Product> products = sql.generateProducts();
            for (int i = 0; i < products.size(); i++){
                sql.addProduct(products.get(i));
            }
            ArrayList<User> users = sql.generateUsers();
            for (int i = 0; i < users.size(); i++){
                sql.addUser(users.get(i));
            }
            sql.addTransaction("Administrator", 120.00, 10, "Royal");
        }
        
        GUI g = new GUI();
        Controller c = new Controller(g);

        g.setVisible(true);
        g.switchToLogin();
        //g.switchToCustomerPage("juan");
    }
}

