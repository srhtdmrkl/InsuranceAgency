public class AddressManager {
   
    static void addAddress(User user, Address address) {
        user.getAddresses().add(address);
    }

    static void removeAddress(User user, Address address) { 
        user.getAddresses().remove(address);
    }

}
