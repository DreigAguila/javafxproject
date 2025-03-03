public class Rider {
    private String riderid;
    private String riderfullname;
    private String ridercontactnumber;
    private String zip;
    private String city;
    private String street;
    private String platenumber;
    private String vehicle;
    private int rating;
    private String shipontime;

    public Rider(String rId, String rFullName, String rContactNumber, String zip, String city, String street, String plateNumber, String vehicle, int rating, String shipontime) {
        this.riderid = rId;
        this.riderfullname = rFullName;
        this.ridercontactnumber = rContactNumber;
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.platenumber = plateNumber;
        this.vehicle = vehicle;
        this.rating = rating;
        this.shipontime = shipontime;
    }

    public String getRiderid() {
        return riderid;
    }

    public void setRiderid(String rId) {
        this.riderid = rId;
    }

    public String getRiderfullname() {
        return riderfullname;
    }

    public void setRiderfullname(String rFullName) {
        this.riderfullname = rFullName;
    }

    public String getRidercontactnumber() {
        return ridercontactnumber;
    }

    public void setRidercontactnumber(String rContactNumber) {
        this.ridercontactnumber = rContactNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String plateNumber) {
        this.platenumber = plateNumber;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getShipontime() {
        return shipontime;
    }

    public void setShipontime(String shipontime) {
        this.shipontime = shipontime;
    }
}