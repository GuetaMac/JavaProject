import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UserManagementLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Map<String, String> userDatabase;

    public UserManagementLoginForm() {
        // Set up the frame
        setTitle("User Management Login Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize user database
        userDatabase = new HashMap<>();
        // Add a default user for demonstration
        userDatabase.put("admin", "admin");

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton addUserButton = new JButton("Add User");
        JButton deleteUserButton = new JButton("Delete User");

        // Set layout
        setLayout(new GridLayout(4, 2));

        // Add components to the frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(addUserButton);
        add(deleteUserButton);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (userDatabase.containsKey(username) && String.valueOf(password).equals(userDatabase.get(username))) {
            JOptionPane.showMessageDialog(this, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Login failed. Please check your credentials.");
        }

        // Clear the fields after login attempt
        usernameField.setText("");
        passwordField.setText("");
    }

    private void addUser() {
        String username = JOptionPane.showInputDialog(this, "Enter new username:");
        if (username != null && !username.isEmpty()) {
            String password = JOptionPane.showInputDialog(this, "Enter password for " + username + ":");
            if (password != null) {
                userDatabase.put(username, password);
                JOptionPane.showMessageDialog(this, "User added successfully!");
            }
        }
    }

    private void deleteUser() {
        String username = JOptionPane.showInputDialog(this, "Enter username to delete:");
        if (username != null && !username.isEmpty()) {
            if (userDatabase.containsKey(username)) {
                userDatabase.remove(username);
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserManagementLoginForm().setVisible(true);
            }
        });
    }
}