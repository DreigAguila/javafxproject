import javafx.beans.property.SimpleStringProperty;

public class Rider {
    private final SimpleStringProperty riderid;
    private final SimpleStringProperty riderfullname;
    private final SimpleStringProperty ridercontactnumber;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty city;
    private final SimpleStringProperty street;
    private final SimpleStringProperty platenumber;
    private final SimpleStringProperty vehicle;
    private final SimpleStringProperty rating;
    private final SimpleStringProperty shipontime;

  
    public Rider (String rId, String rFullName, String rContactNumber, String zip, String city, String street, String plateNumber, String 
    vehicle, String rating, String shipontime){

        this.riderid = new SimpleStringProperty(rId);
        this.riderfullname = new SimpleStringProperty(rFullName);
        this.ridercontactnumber = new SimpleStringProperty(rContactNumber);
        this.zip = new SimpleStringProperty(zip);
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.platenumber = new SimpleStringProperty(plateNumber);
        this.vehicle = new SimpleStringProperty(vehicle);
        this.rating = new SimpleStringProperty(rating);
        this.shipontime = new SimpleStringProperty(shipontime);

    }

    public String getRiderid(){
        return riderid.get();
    }
    public String getRiderfullname(){
        return riderfullname.get();
    }
    public String getRidercontactnumber(){
        return ridercontactnumber.get();
    }
    public String getZip(){
        return zip.get();
    }
    public String getCity(){
        return city.get();
    }
    public String getStreet(){
        return street.get();
    }
    public String getPlatenumber(){
        return platenumber.get();
    }
    public String getVehicle(){
        return vehicle.get();
    }
    public String getRating(){
        return rating.get();
    }
    public String getShipontime(){
        return shipontime.get();
    }
}

