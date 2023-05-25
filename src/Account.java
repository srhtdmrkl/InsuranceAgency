import java.util.ArrayList;
import java.util.Scanner;

public abstract class Account implements Comparable<Account> {

    private Scanner scanner = new Scanner(System.in);
    protected User user;
    private AuthenticationStatus authenticationStatus;
    protected ArrayList<Insurance> insurances;

    public Account(User user) {
        this.user = user;
        authenticationStatus = AuthenticationStatus.NOT_AUTHENTICATED;
        this.insurances = new ArrayList<Insurance>();
    }

    // Show the user information
    public final void showUserInfo() {
        System.out.println("Full Name: " + this.user.getName());
        System.out.println("Email: " + this.user.getEmail());
        System.out.println("Profession: " + this.user.getProfession());
        System.out.println("Age: " + this.user.getAge());
        System.out.print("Smoker: ");
        if (this.user.isSmoker() == null) {
            System.out.println("Unknown");
        } else if (this.user.isSmoker()) {
            System.out.println("Yes");
        } else if (!this.user.isSmoker()) {
            System.out.println("No");
        }
        System.out.println("Last Login Date: " + this.user.getLastLoginDate());
        this.user.listAddresses();
    }

    // Get authentication status
    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

    // Set authentication status
    public void setAuthenticationStatus(AuthenticationStatus authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    // Get user
    public User getUser() {
        return user;
    }

    // Get insurances
    public ArrayList<Insurance> getInsurances() {
        return insurances;
    }

    public static void login(String email, String password) {
        // Implement login functionality in AccountManager class
    }

    // Buy insurance
    public void buyInsurance(Insurance insurance) {
        if (insurance instanceof HealthInsurance) {
            if (getUser().isSmoker() == null) {
                user.smokerStaus();
            }
        }
        double premium = insurance.calculate(this);
        System.out.println("Premium: $" + premium + "per year");
        while (true) {
            System.out.println("Are you sure you want to buy? [Y]es / [N]o");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {

                System.out.println("Insurance purchased successfully!");
                break;
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Insurance purchase canceled.");
                return;
            }
        }
    }

    // Calculate the premium and add insurance to the list
    public void addInsurance(Insurance insurance) {
        insurance.setPremium(insurance.calculate(this));
        insurances.add(insurance);
    }

    // Remove the insurance from the list
    public void removeInsurance(Insurance insurance) {
        while (true) {
            System.out.println("Are you sure you want to remove the insurance? [Y]es / [N]o");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                insurances.remove(insurance);
                System.out.println("Insurance is successfully canceled.");
                return;
            } else if (choice.equalsIgnoreCase("N")) {
                return;
            }
        }

    }

    // List insurances 
    public void listInsurances() {
        if (insurances.isEmpty()) {
            System.out.println("No insurances found.");
        } else {
            for (Insurance insurance : insurances) {
                System.out.print(insurance.toString());
            }
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Account other) {
        return this.getUser().getEmail().compareTo(other.getUser().getEmail());
    }

}
