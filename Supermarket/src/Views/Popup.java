package Views;

import javax.swing.*;

import Drivers.Controller;
import Models.User;

public class Popup {
    public Popup(){}
    
    public void info(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void warning(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public void error(String title, String message){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public boolean yesno(String title, String question){
        if(JOptionPane.showConfirmDialog(null, question, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            return true;
        return false;
    }
    
    public double donation(){ 
        String input = JOptionPane.showInputDialog(null, "Enter the amount that you want to donate to fight hunger and malnutrition.", "Donation", JOptionPane.QUESTION_MESSAGE);
        try{
            return Double.parseDouble(input);
        }catch(Exception e){
            return 0;
        }
    }

    public User newUser(Controller c){
        JTextField username = new JTextField();
        JPasswordField password1 = new JPasswordField();
        JPasswordField password2 = new JPasswordField();
        JTextField fullname = new JTextField();
        JTextField address = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        JLabel password1Label = new JLabel("Password:");
        JLabel password2Label = new JLabel("Confirm Password:");
        JLabel fullnameLabel = new JLabel("Fullname:");
        JLabel addressLabel = new JLabel("Address:");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        panel.add(usernameLabel);
        panel.add(username);
        panel.add(Box.createVerticalStrut(15)); // a spacer
        
        panel.add(password1Label);
        panel.add(password1);
        panel.add(Box.createVerticalStrut(15)); // a spacer

        panel.add(password2Label);
        panel.add(password2);
        panel.add(Box.createVerticalStrut(15)); // a spacer

        panel.add(fullnameLabel);
        panel.add(fullname);
        panel.add(Box.createVerticalStrut(15)); // a spacer
        
        panel.add(addressLabel);
        panel.add(address);
        panel.add(Box.createVerticalStrut(15)); // a spacer

        User u = null;
        while(u == null){
            int result = JOptionPane.showConfirmDialog(null, panel, "Create Customer User Account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if(!c.usernameExists(username.getText())){
                    if(String.valueOf(password1.getPassword()).equals(String.valueOf(password2.getPassword()))){
                        return new User(username.getText(), String.valueOf(password1.getPassword()), 1, fullname.getText(), address.getText());
                    }else{
                        this.warning("New Account", "Passwords don't match!");
                    }
                }else{
                    this.warning("New Account", "Username already exists!");
                }
            }
            if (result == JOptionPane.CANCEL_OPTION){
                return null;
            }
        }
        return u;
    }
}
