import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    private Controller control;

    public Terminal(Controller control){
        this.control = control;
    }

    public final void clearConsole(){ //from https://stackoverflow.com/a/17015039
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                Runtime.getRuntime().exec("cls");
            else
                Runtime.getRuntime().exec("clear");
        }catch (final Exception e){
            //  Handle any exceptions.
        }
    }

    public String[] addProduct(ArrayList<String[]> products) {
        this.clearConsole();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name: ");
        String product_name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double product_price = scanner.nextDouble();
        System.out.print("Enter product stock: ");
        int product_stock = scanner.nextInt();
        String[] product_info = {product_name, Double.toString(product_price), Integer.toString(product_stock)};
        scanner.close();

        control.
        return product_info;
    }

    public void removeProduct(ArrayList<String[]> products) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the product to remove: ");
        String product_name = scanner.nextLine();
        boolean found = false;
        for (String[] product : products) {
            if (product[0].equals(product_name)) {
                products.remove(product);
                found = true;
                System.out.println("Product removed successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
        scanner.close();
    }

    public void displayProducts(ArrayList<String[]> products) {
        System.out.println("Products:");
        System.out.println("Name ------ Price ------ Stock");
        for (String[] product : products) {
            System.out.println(product[0] + " ------ " + product[1] + " ------ " + product[2]);
        }
    }

    public void viewStocks(ArrayList<String[]> products) {
        System.out.println("Available Stocks:");
        System.out.println("Product Name ------ Stock");
        for (String[] product : products) {
            System.out.println(product[0] + " ------ " + product[2]);
        }
    }

    public void searchProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the product to search: ");
        String product_name = scanner.nextLine();
        boolean found = false;
        //sql: product in db
        for (String[] product : products) {
            if (product[0].equals(product_name)) {
                found = true;
                System.out.println("\nProduct found:");
                System.out.println("Name ------ Price ------ Stock\n");
                System.out.println(product[0] + " --- " + product[1] + " --- " + product[2]);
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    public void displayOrders(ArrayList<String[]> transactions) {
        for (String[] transaction : transactions) {
            System.out.println("Orders:");
            System.out.println("Buyer: " + transaction[0]);
            System.out.println("Address: " + transaction[1]);
            System.out.println("Phone Number: " + transaction[2]);
            System.out.println("Item: " + transaction[3]);
            System.out.println("Quantity: " + transaction[4]);
            System.out.println("Total: " + transaction[5]);
            System.out.println("==============================================================================================\n");
        }
    }

    public void sellerMenu(ArrayList<String[]> products, ArrayList<String[]> transactions) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==============================================================================================");
            System.out.println("\t\t\tSimplified Online");
            System.out.println("\t\t\t  Buying System");
            System.out.println("==============================================================================================\n");
            System.out.println("Product Menu");
            System.out.println("1. Add product");
            System.out.println("2. Remove product");
            System.out.println("3. Display all products");
            System.out.println("4. Sort products by price");
            System.out.println("5. View order list");
            System.out.println("6. View stocks");
            System.out.println("7. Update stocks");
            System.out.println("8. Exit\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\tAdd Product");
                System.out.println("==============================================================================================\n");
                addProduct(products);
                System.out.println();
            } else if (choice == 2) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\tRemove Product");
                System.out.println("==============================================================================================\n");
                displayProducts(products);
                removeProduct(products);
                System.out.println();
            } else if (choice == 3) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\tDisplay Product");
                System.out.println("==============================================================================================\n");
                displayProducts(products);
                System.out.println();
            } else if (choice == 4) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\tSort Product");
                System.out.println("==============================================================================================\n");
                sortProducts(products);
                System.out.println();
            } else if (choice == 5) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\t  View Order List");
                System.out.println("==============================================================================================\n");
                displayOrders(transactions);
            } else if (choice == 6) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\t  View Stocks");
                System.out.println("==============================================================================================\n");
                viewStocks(products);
            } else if (choice == 7) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("\t\t\t  Add Stock");
                System.out.println("==============================================================================================\n");
                displayProducts(products);
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter the name of the product to add stock to: ");
                String product_name = scanner.nextLine();
                System.out.print("Enter the additional stock quantity: ");
                int additional_stock = scanner.nextInt();
                addStock(products, product_name, additional_stock);
                System.out.println("Stock for " + product_name + " has been updated.");
            } else if (choice == 8) {
                System.out.println(" Thank you for using online shop app! ");
                break;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }

    public void customerMenu(ArrayList<String[]> products, ArrayList<String[]> transactions) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter your name: ");
        String customer_name = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        while (true) {
            System.out.println("==============================================================================================");
            System.out.println("\t\t\tOnline Buying System");
            System.out.println("==============================================================================================\n");
            System.out.println("1. Place an order");
            System.out.println("2. Search product");
            System.out.println("3. Exit\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("==============================================================================================\n");
                displayProducts(products);
                System.out.println();
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tCustomer Information");
                System.out.println("==============================================================================================\n");
                System.out.println("Your name: " + customer_name);
                System.out.println("Your address: " + address);
                System.out.println("Your phone number: " + phone);
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tCustomer Order");
                System.out.println("==============================================================================================\n");

                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter the product name: ");
                String product_name = scanner.nextLine();
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                boolean found = false;
                for (String[] product : products) {
                    if (product[0].equals(product_name)) {
                        found = true;
                        double price = Double.parseDouble(product[1]);
                        int stock = Integer.parseInt(product[2]);

                        System.out.print("Would you like to donate for hunger and malnutrition? (Y/N): ");
                        scanner.nextLine(); // Consume the newline character
                        String response = scanner.nextLine();

                        double amount = 0.0;

                        if (response.equalsIgnoreCase("Y")) {
                            System.out.print("How much would you like to donate? ");
                            amount = scanner.nextDouble();
                            System.out.println("Thank you for your donation of Php " + amount + ".\n");
                        }
                        double total_cost = (quantity * price) + amount;

                        String[] new_transaction = {customer_name, address, phone, product_name, Integer.toString(quantity), Double.toString(total_cost)};
                        transactions.add(new_transaction);

                        stock -= quantity;
                        product[2] = Integer.toString(stock);

                        System.out.println("==============================================================================================");
                        System.out.println("\t\t\tOrder Summary");
                        System.out.println("==============================================================================================\n");
                        System.out.println("Customer name: " + new_transaction[0]);
                        System.out.println("Address: " + new_transaction[1]);
                        System.out.println("Phone Number: " + new_transaction[2]);
                        System.out.println("Product name: " + new_transaction[3]);
                        System.out.println("Quantity: " + new_transaction[4]);
                        System.out.println("Price per item: Php " + price);

                        if (amount > 0.0) {
                            System.out.println("Donation amount: Php " + amount);
                        }

                        System.out.println("Total cost: Php " + new_transaction[5] + "\n");
                    } else {
                        System.out.println("Not enough stock available.");
                    }
                }
                if (!found) {
                    System.out.println("Product not found.");
                }
            } else if (choice == 2) {
                System.out.println("==============================================================================================");
                System.out.println("\t\t\tOnline Buying System");
                System.out.println("==============================================================================================\n");
                searchProduct(products);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
}
