package Views;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import Models.Product;

public class Customer extends javax.swing.JPanel{
    JLabel title, productName, productPrice, productQuantity, productList, cartList;
    JTextField name, price, quantity;
    JButton search, add, remove, logout, checkout;
    JTextArea productListText, cartListText;

    public Customer(ActionListener ac, String loggedUser, ArrayList<Product> products){ //Constructor
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
        productList.setBounds(0+20,20+(60*4), 200,40);
        productList.setVerticalAlignment(SwingConstants.CENTER);
        productList.setHorizontalAlignment(SwingConstants.LEFT);
        productList.setFont(v.labelFont);
        add(productList);

        cartList = new JLabel();
        cartList.setText("Cart List: ");
        cartList.setBounds(0+400+10,20+(60*4), 400,40);
        cartList.setVerticalAlignment(SwingConstants.CENTER);
        cartList.setHorizontalAlignment(SwingConstants.LEFT);
        cartList.setFont(v.labelFont);
        add(cartList);

        name = new JTextField();
        name.setBounds(200, 20+60, 350, 40);
        name.setFont(v.inputFont);
        add(name);

        price = new JTextField();
        price.setBounds(200, 20+(60*2), 350, 40);
        price.setFont(v.inputFont);
        price.setEditable(false);
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

        checkout = new JButton();
        checkout.setFont(v.btnFont);
        checkout.setBounds(800/2-60, 500, 200, 40);
        checkout.setText("Checkout");
        checkout.addActionListener(ac);
        checkout.setActionCommand("checkoutCommand");
        add(checkout);

        search = new JButton();
        search.setFont(v.btnFont);
        search.setBounds(800/2+160, 20+(60*1), 200, 40);
        search.setText("Search");
        search.addActionListener(ac);
        search.setActionCommand("searchCustomerProductCommand");
        add(search);

        add = new JButton();
        add.setFont(v.btnFont);
        add.setBounds(800/2+160, 20+(60*2), 200, 40);
        add.setText("Add");
        add.addActionListener(ac);
        add.setActionCommand("addToCartCommand");
        add(add);

        remove = new JButton();
        remove.setFont(v.btnFont);
        remove.setBounds(800/2+160, 20+(60*3), 200, 40);
        remove.setText("Remove");
        remove.addActionListener(ac);
        remove.setActionCommand("removeFromCartCommand");
        add(remove);

        productListText = new JTextArea();
        productListText.setFont(v.textAreaFont);
        productListText.setLineWrap(true);
        productListText.setWrapStyleWord(true);
        productListText.setBounds(20, 20+(60*5), (740/2)-20, 160);
        productListText.setEditable(false);
        JScrollPane scroll = new JScrollPane (productListText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(productListText.getX(), productListText.getY(), productListText.getWidth(), productListText.getHeight());
        add(scroll);

        cartListText = new JTextArea();
        cartListText.setFont(v.textAreaFont);
        cartListText.setLineWrap(true);
        cartListText.setWrapStyleWord(true);
        cartListText.setBounds(20+(740/2)+20, 20+(60*5), (740/2)-20, 160);
        cartListText.setEditable(false);
        JScrollPane scroll2 = new JScrollPane (cartListText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll2.setBounds(cartListText.getX(), cartListText.getY(), cartListText.getWidth(), cartListText.getHeight());
        add(scroll2);

        revalidate();
        updateProductList(products);
    }

   public String getProductName(){
    // Retrieve and return the text entered in the 'name' field (assuming 'name' is a JTextField or similar component)
    return name.getText();
}

public int getProductQuantity(){
    // Check if the 'quantity' field is empty; if so, show a warning and return 0
    if(quantity.getText().length() == 0)
        new Popup().warning("Product Quantity", "Please enter a valid quantity value.");

    // Parse and return the integer value entered in the 'quantity' field
    return Integer.parseInt(quantity.getText());
}

public void setProductInfo(Product p){
    // Set the text in the 'name' field to the product name and display the price in the 'price' field
    name.setText(p.getName());
    price.setText("Php " + p.getPrice() + "");
}

public void updateProductList(ArrayList<Product> products){
    // Clear the existing text in 'productListText'
    productListText.setText("");

    // Iterate through the list of products and append their summaries to 'productListText'
    for(int i = 0; i < products.size(); i++)
        productListText.append(products.get(i).getSummary() + "\n");
}

public void updateCartList(ArrayList<Product> products){
    // Clear the existing text in 'cartListText'
    cartListText.setText("");

    // Iterate through the list of products and append their summaries to 'cartListText'
    for(int i = 0; i < products.size(); i++)
        cartListText.append(products.get(i).getSummary() + "\n");
}

public void clearCart(){
    // Clear the existing text in 'cartListText' to empty the cart display
    cartListText.setText("");
}
}
