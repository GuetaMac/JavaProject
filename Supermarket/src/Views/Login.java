package Views;

import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends javax.swing.JPanel{
    JLabel title, usernameLabel, passwordLabel;
    JTextField username;
    JPasswordField password;
    JButton login, signup, exit;

    public Login(ActionListener ac){ //Constructor
        setLayout(null);
        Values v = new Values(); //pang font

        title = new JLabel();
        title.setText("Supermarket");
        title.setBounds(0+20,20, 200,40);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setFont(v.titleFont);
        add(title);

        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameLabel.setBounds(100,200, 150,40);
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usernameLabel.setFont(v.titleFont);
        add(usernameLabel);

        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordLabel.setBounds(100,300, 150,40);
        passwordLabel.setVerticalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordLabel.setFont(v.titleFont);
        add(passwordLabel);
        
        username = new JTextField();
        username.setBounds(100+150, 200, 400, 40);
        username.setFont(v.inputFont);
        add(username);

        password = new JPasswordField();
        password.setBounds(100+150, 300, 400, 40);
        password.setFont(v.inputFont);
        add(password);

        signup = new JButton();
        signup.setFont(v.btnFont);
        signup.setBounds(120+(120*1)+60, 400, 160, 40);
        signup.setText("Signup");
        signup.addActionListener(ac);
        signup.setActionCommand("signupCommand");
        add(signup);

        login = new JButton();
        login.setFont(v.btnFont);
        login.setBounds(120, 400, 160, 40);
        login.setText("Login");
        login.addActionListener(ac);
        login.setActionCommand("loginCommand");
        add(login);

        exit = new JButton();
        exit.setFont(v.btnFont);
        exit.setBounds(120+(120*2)+(60*2), 400, 160, 40);
        exit.setText("Exit");
        exit.addActionListener(ac);
        exit.setActionCommand("exitCommand");
        add(exit);

        revalidate();
    }

    public String getUsername(){
        return username.getText();
    }

    public String getPassword(){
        return String.valueOf(password.getPassword()); //convert char[] to string
    }
}
