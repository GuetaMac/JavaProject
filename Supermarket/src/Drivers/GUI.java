package Drivers;
import javax.swing.*;
import java.awt.*;
import Views.*;

public class GUI extends JFrame {
    public Controller c;
    private Login loginPage;
    private Employee employeePage;
    private Customer customerPage;

    public GUI(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supermarket");
        setName("Window"); // NOI18N
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        pack();
    }

    public void setController(Controller c){
        this.c = c;
    }

    public void switchToLogin(){
        loginPage = new Login(c);
        getContentPane().removeAll();
        getContentPane().add(loginPage);
        revalidate();
    }

    public Login getLoginPage(){
        return loginPage;
    }

    public void switchToEmployeePage(String username){
        employeePage = new Employee(c, username, c.getProducts());
        getContentPane().removeAll();
        getContentPane().add(employeePage);
        revalidate();
    }

    public Employee getEmployeePage(){
        return employeePage;
    }

    public void switchToCustomerPage(String username){
        customerPage = new Customer(c, username, c.getProducts());
        getContentPane().removeAll();
        getContentPane().add(customerPage);
        revalidate();
    }

    public Customer getCustomerPage(){
        return customerPage;
    }
}
