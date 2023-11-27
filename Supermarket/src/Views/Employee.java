package Views;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Models.Product;

public class Employee extends javax.swing.JPanel{
    JLabel title, productName, productPrice, productQuantity, productList;
    JTextField name, price, quantity;
    JButton add, remove, edit, search, logout, pageSales;
    JTextArea productListText;

    public Employee(ActionListener ac, String loggedUser, ArrayList<Product> products){ //Constructor
        setLayout(null);
        Values v = new Values(); //pang font

        title = new JLabel();
        title.setText("Supermarket - Welcome " + loggedUser);
        title.setBounds(0+20,20, 600,40);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setFont(v.titleFont);
        add(title);

        productName = new JLabel();
        productName.setText("Product Name: ");
        productName.setBounds(0+20,20+60, 400,40);
        productName.setVerticalAlignment(SwingConstants.CENTER);
        productName.setHorizontalAlignment(SwingConstants.LEFT);
        productName.setFont(v.labelFont);
        add(productName);

        productPrice = new JLabel();
        productPrice.setText("Product Price: ");
        productPrice.setBounds(0+20,20+(60*2), 400,40);
        productPrice.setVerticalAlignment(SwingConstants.CENTER);
        productPrice.setHorizontalAlignment(SwingConstants.LEFT);
        productPrice.setFont(v.labelFont);
        add(productPrice);

        productQuantity = new JLabel();
        productQuantity.setText("Product Quantity: ");
        productQuantity.setBounds(0+20,20+(60*3), 400,40);
        productQuantity.setVerticalAlignment(SwingConstants.CENTER);
        productQuantity.setHorizontalAlignment(SwingConstants.LEFT);
        productQuantity.setFont(v.labelFont);
        add(productQuantity);

        productList = new JLabel();
        productList.setText("Product List: ");
        productList.setBounds(0+20,20+(60*4), 400,40);
        productList.setVerticalAlignment(SwingConstants.CENTER);
        productList.setHorizontalAlignment(SwingConstants.LEFT);
        productList.setFont(v.labelFont);
        add(productList);

        name = new JTextField();
        name.setBounds(200, 20+60, 350, 40);
        name.setFont(v.inputFont);
        add(name);

        price = new JTextField();
        price.setBounds(200, 20+(60*2), 350, 40);
        price.setFont(v.inputFont);
        add(price);

        quantity = new JTextField();
        quantity.setBounds(200, 20+(60*3), 350, 40);
        quantity.setFont(v.inputFont);
        add(quantity);

        logout = new JButton();
        logout.setFont(v.btnFont);
        logout.setBounds(800/2+160, 500, 200, 40);
        logout.setText("Logout");
        logout.addActionListener(ac);
        logout.setActionCommand("logoutCommand");
        add(logout);

        pageSales = new JButton();
        pageSales.setFont(v.btnFont);
        pageSales.setBounds(0+20, 500, 200, 40);
        pageSales.setText("Sales Page");
        pageSales.addActionListener(ac);
        pageSales.setActionCommand("switchToSalesCommand");
        add(pageSales);

        add = new JButton();
        add.setFont(v.btnFont);
        add.setBounds(800/2+160, 20+(60*1), 200, 40);
        add.setText("Add");
        add.addActionListener(ac);
        add.setActionCommand("addStockCommand");
        add(add);

        search = new JButton();
        search.setFont(v.btnFont);
        search.setBounds(800/2+160, 20+(60*2), 200, 40);
        search.setText("Search");
        search.addActionListener(ac);
        search.setActionCommand("searchEmployeeProductCommand");
        add(search);
        
        edit = new JButton();
        edit.setFont(v.btnFont);
        edit.setBounds(800/2+160, 20+(60*3), 200, 40);
        edit.setText("Edit");
        edit.addActionListener(ac);
        edit.setActionCommand("editStockCommand");
        add(edit);

        remove = new JButton();
        remove.setFont(v.btnFont);
        remove.setBounds(800/2+160, 20+(60*4), 200, 40);
        remove.setText("Remove");
        remove.addActionListener(ac);
        remove.setActionCommand("removeStockCommand");
        add(remove);

        productListText = new JTextArea();
        productListText.setFont(v.textAreaFont);
        productListText.setLineWrap(true);
        productListText.setWrapStyleWord(true);
        productListText.setBounds(20, 20+(60*5), 740, 160);
        productListText.setEditable(false);
        JScrollPane scroll = new JScrollPane (productListText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(productListText.getX(), productListText.getY(), productListText.getWidth(), productListText.getHeight());
        add(scroll);

        revalidate();
        updateProductList(products);
    }

    public void updateProductList(ArrayList<Product> products){
    // Clear the existing text in 'productListText' to refresh the display
    productListText.setText("");

    // Iterate through the list of products and append their summaries to 'productListText'
    for(int i = 0; i < products.size(); i++)
        productListText.append(products.get(i).getSummary() + "\n");
    }

    public String getProductName(){
        // Retrieve and return the text entered in the 'name' field (assuming 'name' is a JTextField or similar component)
        return name.getText();
    }

    public double getProductPrice(){
        // Check if the 'price' field is empty; if so, return 0, otherwise, parse and return the double value entered
        if(price.getText().equals(""))
            return 0;
        return Double.parseDouble(price.getText());
    }

    public int getProductQuantity(){
        // Check if the 'quantity' field is empty; if so, return 0, otherwise, parse and return the integer value entered
        if(quantity.getText().equals(""))
            return 0;
        return Integer.parseInt(quantity.getText());
    }

    public void setProductInfo(Product p){
        // Set the 'name' field to the product name, 'price' field to the product price, and 'quantity' field to the product quantity
        name.setText(p.getName());
        price.setText(p.getPrice()+"");
        quantity.setText(p.getQuantity()+"");
    }
}


