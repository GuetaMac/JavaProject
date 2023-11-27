package Drivers;
import javax.swing.*;
import java.awt.*;
import Views.*;

public class GUI extends JFrame {
    public Controller c;
    private Login loginPage;
    private Employee employeePage;
    private Customer customerPage;
    private Sales salesPage;

    public GUI(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supermarket");
        setName("Window");
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        pack();
    }

    public void setController(Controller c){
        this.c = c;
    }

  public void switchToLogin(){
    // Create a new instance of the Login page
    this.loginPage = new Login(c);

    // Remove all components from the current content pane
    getContentPane().removeAll();

    // Add the newly created Login page to the content pane
    getContentPane().add(this.loginPage);

    // Revalidate the content pane to reflect the changes
    revalidate();
}

public Login getLoginPage(){
    // Return the current instance of the Login page
    return this.loginPage;
}

public void switchToEmployeePage(String username){
    // Create a new instance of the Employee page with the specified username and product information
    this.employeePage = new Employee(c, username, c.getProducts());

    // Remove all components from the current content pane
    getContentPane().removeAll();

    // Add the newly created Employee page to the content pane
    getContentPane().add(this.employeePage);

    // Revalidate the content pane to reflect the changes
    revalidate();
}

public Employee getEmployeePage(){
    // Return the current instance of the Employee page
    return this.employeePage;
}

public void switchToSalesPage(String username){
    // Create a new instance of the Sales page with the specified username
    this.salesPage = new Sales(c, username);

    // Remove all components from the current content pane
    getContentPane().removeAll();

    // Add the newly created Sales page to the content pane
    getContentPane().add(this.salesPage);

    // Revalidate the content pane to reflect the changes
    revalidate();
}

public Sales getSalesPage(){
    // Return the current instance of the Sales page
    return this.salesPage;
}

public void switchToCustomerPage(String username){
    // Create a new instance of the Customer page with the specified username and product information
    this.customerPage = new Customer(c, username, c.getProducts());

    // Remove all components from the current content pane
    getContentPane().removeAll();

    // Add the newly created Customer page to the content pane
    getContentPane().add(this.customerPage);

    // Revalidate the content pane to reflect the changes
    revalidate();
}

public Customer getCustomerPage(){
    // Return the current instance of the Customer page
    return customerPage;
}
}
