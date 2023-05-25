import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class User {
    private Scanner scanner = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profession;
    private int age;
    private Boolean smoker;
    private Date lastLoginDate;
    private ArrayList<Address> addresses;

    public User(String firstName, String lastName, String email, String password, int age, String profession) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.profession = profession;
        this.addresses = new ArrayList<Address>();
    }

    public String getName(){
        return firstName + " " +lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getProfession(){
        return profession;
    }

    public int getAge(){
        return age;
    }

    public Boolean isSmoker(){
        return smoker;
    }

    public void setSmoker(boolean isSmoker){
        this.smoker = isSmoker;
    }

    public Date getLastLoginDate(){
        return lastLoginDate;
    }

    public void setLastLoginDate(){
        this.lastLoginDate = new Date();
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void listAddresses() {
        for (Address address : addresses) {
            String prefix = "";
            if (address instanceof HomeAddress) {
                prefix = "Home Address: ";
            } else if(address instanceof BusinessAddress) {
                prefix = "Business Address: ";
            }
            System.out.println(prefix + address.toString());
        }
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void removeAddress(Address address) {
            addresses.remove(address);
        }


    // Ask the user if they are smokers or not
    public void smokerStaus(){
        if (isSmoker() == null) {
            while (true) {
                System.out.println("Are you a smoker? ([Y]es / [N]o):");
                String isSmoker = scanner.next();
                if (isSmoker.equalsIgnoreCase("Y")) {
                    setSmoker(true);
                    break;
                } else if (isSmoker.equalsIgnoreCase("N")) {
                    setSmoker(false);
                    break;
                }
            }
        }
    }
    
}
