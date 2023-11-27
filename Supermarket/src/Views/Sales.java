package Views;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Models.Transaction;


public class Sales extends javax.swing.JPanel{
    JLabel title, searchName, mode, productPrice, salesQuantity, sales;
    JTextField name, price, quantity;
    JButton add, remove, edit, logout, stocksPage;
    JTextArea salesText;
    JCheckBox searchMode;

    public Sales(ActionListener ac, String loggedUser){ //Constructor
        setLayout(null);
        Values v = new Values(); //pang font

        title = new JLabel();
        title.setText("Supermarket - Welcome " + loggedUser);
        title.setBounds(0+20,20, 600,40);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setFont(v.titleFont);
        add(title);

        searchName = new JLabel();
        searchName.setText("Search Name/ID: ");
        searchName.setBounds(0+20,20+60, 400,40);
        searchName.setVerticalAlignment(SwingConstants.CENTER);
        searchName.setHorizontalAlignment(SwingConstants.LEFT);
        searchName.setFont(v.labelFont);
        add(searchName);

        mode = new JLabel();
        mode.setText("User Search Mode: ");
        mode.setBounds(800/2+160, 20+(60*2), 300, 40);
        mode.setVerticalAlignment(SwingConstants.CENTER);
        mode.setHorizontalAlignment(SwingConstants.LEFT);
        mode.setFont(v.labelFont);
        add(mode);

        searchMode = new JCheckBox();
        searchMode.setBounds(800/2+330, 20+(60*2), 50, 40);
        searchMode.setVerticalAlignment(SwingConstants.CENTER);
        searchMode.setHorizontalAlignment(SwingConstants.LEFT);
        searchMode.setFont(v.labelFont);
        add(searchMode);

        productPrice = new JLabel();
        productPrice.setText("Total Sales: ");
        productPrice.setBounds(0+20,20+(60*2), 400,40);
        productPrice.setVerticalAlignment(SwingConstants.CENTER);
        productPrice.setHorizontalAlignment(SwingConstants.LEFT);
        productPrice.setFont(v.labelFont);
        add(productPrice);

        salesQuantity = new JLabel();
        salesQuantity.setText("Total Volume: ");
        salesQuantity.setBounds(0+20,20+(60*3), 400,40);
        salesQuantity.setVerticalAlignment(SwingConstants.CENTER);
        salesQuantity.setHorizontalAlignment(SwingConstants.LEFT);
        salesQuantity.setFont(v.labelFont);
        add(salesQuantity);

        sales = new JLabel();
        sales.setText("Search Results: ");
        sales.setBounds(0+20,20+(60*4), 400,40);
        sales.setVerticalAlignment(SwingConstants.CENTER);
        sales.setHorizontalAlignment(SwingConstants.LEFT);
        sales.setFont(v.labelFont);
        add(sales);

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
        quantity.setEditable(false);
        add(quantity);

        logout = new JButton();
        logout.setFont(v.btnFont);
        logout.setBounds(800/2+160, 500, 200, 40);
        logout.setText("Logout");
        logout.addActionListener(ac);
        logout.setActionCommand("logoutCommand");
        add(logout);

        stocksPage = new JButton();
        stocksPage.setFont(v.btnFont);
        stocksPage.setBounds(0+20, 500, 200, 40);
        stocksPage.setText("Stocks Page");
        stocksPage.addActionListener(ac);
        stocksPage.setActionCommand("switchToStocksCommand");
        add(stocksPage);
        
        add = new JButton();
        add.setFont(v.btnFont);
        add.setBounds(800/2+160, 20+(60*1), 200, 40);
        add.setText("Search");
        add.addActionListener(ac);
        add.setActionCommand("searchSalesCommand");
        add(add);

        salesText = new JTextArea();
        salesText.setFont(v.textAreaFont_small);
        salesText.setLineWrap(true);
        salesText.setWrapStyleWord(true);
        salesText.setBounds(20, 20+(60*5), 740, 160);
        salesText.setEditable(false);
        JScrollPane scroll = new JScrollPane (salesText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(salesText.getX(), salesText.getY(), salesText.getWidth(), salesText.getHeight());
        add(scroll);

        revalidate();
        //updateProductList(products);
    }

  // Method to update the sales list display based on a list of transactions.
public void updateSalesList(ArrayList<Transaction> transactions){
    salesText.setText(""); // Clears the existing text in the salesText component.
    
    // Appends the verbose summary of each transaction to the salesText component.
    for(int i = 0; i < transactions.size(); i++)
        salesText.append(transactions.get(i).getVerboseSummary() + "\n");
}

// Method to clear the sales list display.
public void clearSalesList(){
    salesText.setText(""); // Clears the existing text in the salesText component.
}

// Method to update the sales data display with the provided quantity and subtotal values.
public void updateSalesData(int quantity, double subtotal){
    this.quantity.setText(quantity + "pc(s)."); // Sets the text in the quantity component.
    this.price.setText("Php " + subtotal); // Sets the text in the price component.
}

// Method to clear the sales data display.
public void clearSalesData(){
    this.quantity.setText(""); // Clears the text in the quantity component.
    this.price.setText(""); // Clears the text in the price component.
}

// Method to get the search name from the name component.
public String getSearchName(){
    return name.getText(); // Retrieves the text from the name component.
}

// Method to get the state of the search mode (selected or not) from the searchMode checkbox.
public boolean getModeState(){
    return this.searchMode.isSelected(); // Returns true if the searchMode checkbox is selected; otherwise, returns false.
}
}

