
public interface Address {
    
    String getAddressLine();
    String getCity();
    String getZip();

}

class BusinessAddress implements Address{

    private String addressLine;
    private String city;
    private String zip;

    public BusinessAddress(String addressLine, String city, String zip) {
        this.addressLine = addressLine;
        this.city = city;
        this.zip = zip;
    }

    @Override
    public String getAddressLine() {
        return addressLine;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getZip() {
        return zip;
    }

    @Override
    public String toString() {
        return addressLine + ", " + city + " " + zip;
    }
}


class HomeAddress implements Address{
    
    private String addressLine;
    private String city;
    private String zip;

    public HomeAddress(String addressLine, String city, String zip) {
        this.addressLine = addressLine;
        this.city = city;
        this.zip = zip;
    }
    
    @Override
    public String getAddressLine() {
        return addressLine;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getZip() {
        return zip;
    }

    @Override
    public String toString() {
        return addressLine + ", " + city + " " + zip;
    }
}

