import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty userid;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty accountcreated;

    public User(String uid, String uname, String pword, String acreated){

        this.userid = new SimpleStringProperty(uid);
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pword);
        this.accountcreated = new SimpleStringProperty(acreated);

    }
    public String getUserid(){
        return userid.get();
    }
    public String getUsername(){
        return username.get();
    }
    public String getPassword(){
        return password.get();
    }
    public String getAccountcreated(){
        return accountcreated.get();
    }
}
