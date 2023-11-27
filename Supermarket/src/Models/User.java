package Models;

public class User{
    private int userID;
    private String username;
    private String password;
    private String name;
    private String address;
    private int type;

    public User(int userID, String username, String password, int type, String name, String address){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.type = type;
    }
    
    public User(String username, String password, int type, String name, String address){
        this.userID = -1;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public int getUserID(){
        return this.userID;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }

    public int getType(){
        return this.type;
    }

    public void updateUserID(int userID){
        this.userID = userID;
    }

    public void updateUsername(String newUsername){
        this.username = newUsername;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void updateName(String newName){
        this.name = newName;
    }

    public void updateAddress(String newAddress){
        this.address = newAddress;
    }

    public void updateType(int type){
        this.type = type;
    }

    public String getSummary(){
        return "Username: " + this.username + "\nName: " + this.name + "\nAddress: " + this.address + "\n";
    }

    public void printSummary(){
        System.out.println(getSummary());
    }
}