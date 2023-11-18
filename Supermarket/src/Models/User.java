package Models;

public class User{
    private String username;
    private String password;
    private String name;
    private String address;
    private int type;

    public User(String username, String password, int type, String name, String address){
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.type = type;
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
}