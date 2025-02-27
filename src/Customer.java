import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleStringProperty customerid;
    private final SimpleStringProperty customerfullname;
    private final SimpleStringProperty customerpassword;
    private final SimpleStringProperty city;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty street;
    private final SimpleStringProperty contactnumber;
    private final SimpleStringProperty email;
  
    public Customer (String cId, String cFullName, String cpass, String city, String zip, String street, String cNumber, String cEmail){

        this.customerid = new SimpleStringProperty(cId);
        this.customerfullname = new SimpleStringProperty(cFullName);
        this.customerpassword = new SimpleStringProperty(cpass);
        this.city = new SimpleStringProperty(city);
        this.zip = new SimpleStringProperty(zip);
        this.street = new SimpleStringProperty(street);
        this.contactnumber = new SimpleStringProperty(cNumber);
        this.email = new SimpleStringProperty(cEmail);

    }

    public String getCustomerid(){
        return customerid.get();
    }
    public String getCustomerfullname(){
        return customerfullname.get();
    }
    public String getPassword(){
        return customerpassword.get();
    }
    public String getCity(){
        return city.get();
    }
    public String getZip(){
        return zip.get();
    }
    public String getStreet(){
        return street.get();
    }
    public String getContactnumber(){
        return contactnumber.get();
    }
    public String getEmail(){
        return email.get();
    }

}
