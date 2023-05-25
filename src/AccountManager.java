import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.time.LocalDate;

public class AccountManager {
    private Scanner scanner = new Scanner(System.in);
    private TreeSet<Account> accounts;

    public void run() {
        initializeAccounts();
        showLoginPanel();
    }

    // Populate the account list with an Individual Account
    private void initializeAccounts() {
        // Create a new user
        User user = new User("John", "Doe", "johndoe@example.com", "password123", 35, "Engineer");
        user.setSmoker(true);
        Address homeAddress = new HomeAddress("123 Main St", "Anytown", "12345");
        user.addAddress(homeAddress);

        HealthInsurance healthInsurance = new HealthInsurance(LocalDate.of(2023, 1, 1));

        Individual individualAccount = new Individual(user);
        individualAccount.addInsurance(healthInsurance);

        // Add the account to the accounts TreeSet
        accounts = new TreeSet<>();
        accounts.add(individualAccount);
    }

    // Panel to log in or sign up
    private void showLoginPanel() {
        while (true) {
            System.out.println("------------------------");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            System.out.println("------------------------");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        loginMenu(); // log in
                        break;
                    case 2:
                        signUpMenu(); // sign up a new account
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    // Log in menu for accounts
    private void loginMenu() {
        while (true) {
            System.out.println("------------------------");
            System.out.println("E-mail: ");
            String email = scanner.next();
            System.out.println("Password: ");
            String password = scanner.next();
            Account loggedAccount = login(email, password);
            if (loggedAccount != null) {
                showMainPanel(loggedAccount);
                break;
            }
        }
    }

    // Sign up menu to for new users and accounts
    private void signUpMenu() {
        while (true) {
            System.out.println("------------------------");
            System.out.println("1. Individual");
            System.out.println("2. Enterprise");
            System.out.println("Please select your account type: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        signUpIndividual();
                        return;
                    case 2:
                        signUpEnterprise();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    // Create new individual accounts
    private void signUpIndividual() {
        scanner.nextLine();

        // Use getInput() to get the parameters and ensure that they are valid
        String firstName = getInput("First Name: ");
        String lastName = getInput("Last Name: ");

        int age;
        while (true) {
            try {
                System.out.print("Age: ");
                age = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid age input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        String profession = getInput("Profession: ");
        String email = getInput("Email: ");
        String password = getInput("Password: ");

        String addressLine = getInput("Address Line: ");
        String city = getInput("City: ");
        String zip = getInput("Zip: ");

        User user = new User(firstName, lastName, email, password, age, profession);
        System.out.println("User has been created. You can use your email address and password to log in.");

        HomeAddress homeAddress = new HomeAddress(addressLine, city, zip);
        user.addAddress(homeAddress);
        Individual newAccount = new Individual(user);
        accounts.add(newAccount);
    }

    // Create new enterprise accounts
    private void signUpEnterprise() {
        scanner.nextLine();

        String company = getInput("Company Name: ");

        signUpIndividual(); // Reuse individual sign-up process
        User user = accounts.last().getUser();
        accounts.remove(accounts.last()); // Remove the individual account

        BusinessAddress businessAddress = new BusinessAddress(user.getAddresses().get(0).getAddressLine(),
                user.getAddresses().get(0).getCity(), user.getAddresses().get(0).getZip());
        user.removeAddress(user.getAddresses().get(0));
        user.addAddress(businessAddress);
        Enterprise enterprise = new Enterprise(user, company);
        accounts.add(enterprise);
    }

    // Main panel after logging in
    public void showMainPanel(Account loggedAccount) {
        while (true) {
            System.out.println("------------------------");
            System.out.println("User Panel: " + loggedAccount.getUser().getName());
            System.out.println("1. User Details");
            System.out.println("2. Insurance Transactions");
            System.out.println("3. Sign out");
            System.out.println("------------------------");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        showUserSettings(loggedAccount);
                        break;
                    case 2:
                        showInsuranceSettings(loggedAccount);
                        break;
                    case 3:
                        loggedAccount.setAuthenticationStatus(AuthenticationStatus.NOT_AUTHENTICATED); // Removes
                                                                                                       // authentication
                                                                                                       // to sign the
                                                                                                       // user out
                        showLoginPanel(); // Back to login panel
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    // Menu for user details and adding / deleting addresses
    public void showUserSettings(Account loggedAccount) {
        while (true) {
            System.out.println("------------------------");
            System.out.println("User Details");
            System.out.println("1. Show User Information");
            System.out.println("2. Add New Address");
            System.out.println("3. Delete Address");
            System.out.println("4. Back");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        loggedAccount.showUserInfo();
                        break;
                    case 2:
                        System.out.println("Address Line:");
                        scanner.nextLine();
                        String addressLine = scanner.nextLine();
                        System.out.println("City:");
                        String city = scanner.nextLine();
                        System.out.println("Zip Code:");
                        String zipCode = scanner.nextLine();
                        if (loggedAccount instanceof Individual) {
                            HomeAddress newAddress = new HomeAddress(addressLine, city, zipCode);
                            loggedAccount.getUser().addAddress(newAddress);
                            System.out.println("New Home Address added: " + newAddress);
                        } else if (loggedAccount instanceof Enterprise) {
                            BusinessAddress newAddress = new BusinessAddress(addressLine, city, zipCode);
                            loggedAccount.getUser().addAddress(newAddress);
                            System.out.println("New Business Address added: " + newAddress);
                        }
                        break;
                    case 3:
                        List<Address> addresses = loggedAccount.getUser().getAddresses();
                        if (addresses.size() == 0) {
                            System.out.println("No addresses to delete.");
                            break;
                        }
                        System.out.println("Addresses:");
                        for (int i = 0; i < addresses.size(); i++) {
                            System.out.println((i + 1) + ". " + addresses.get(i));
                        }
                        System.out.println("0. Cancel");

                        while (true) {
                            System.out.println("Select an address to delete: ");
                            try {
                                int addressIndex = scanner.nextInt();
                                if (addressIndex > 0 && addressIndex < addresses.size()) {
                                    loggedAccount.getUser().removeAddress(addresses.get(addressIndex-1));
                                    System.out.println("Address deleted successfully.");
                                    break;
                                } else if (addressIndex == 0) {
                                    break;
                                } else {
                                    System.out.println("Invalid selection.");
                                    scanner.nextLine();
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid choice.");
                                scanner.nextLine();
                            }
                        }
                        break;

                    case 4:
                        showMainPanel(loggedAccount);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    // Menu for insurance transactions
    public void showInsuranceSettings(Account loggedAccount) {
        while (true) {
            System.out.println("------------------------");
            System.out.println("Insurance Transactions");
            System.out.println("1. List Insurances");
            System.out.println("2. Buy Insurance");
            System.out.println("3. Cancel Insurance");
            System.out.println("4. Back");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        loggedAccount.listInsurances();
                        break;
                    case 2:
                        buyInsurance(loggedAccount);
                        break;
                    case 3:
                        cancelInsurance(loggedAccount);
                        break;
                    case 4:
                        showMainPanel(loggedAccount);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    // Buy an insurance
    public void buyInsurance(Account loggedAccount) {
        while (true) {
            System.out.println("Select insurance type:");
            System.out.println("1. Health Insurance");

            // If Enterprise account, show 'Back' as 2nd option
            if (loggedAccount instanceof Enterprise) {
                System.out.println("2. Back");
            }

            // If Individual account, show more insurances
            if (loggedAccount instanceof Individual) {
                System.out.println("2. Travel Insurance");
                System.out.println("3. Property Insurance");
                System.out.println("4. Back");
            }

            try {
                int insuranceType = scanner.nextInt();

                switch (insuranceType) {
                    case 1:
                        HealthInsurance healthInsurance = new HealthInsurance(LocalDate.now());
                        loggedAccount.buyInsurance(healthInsurance);
                        break;
                    case 2:
                        if (loggedAccount instanceof Enterprise) {
                            return;
                        } else if (loggedAccount instanceof Individual) {
                            // Implement method to buy a travel insurance :
                            // account.buyInsurance(travelinsurance);
                        } else {
                            System.out.println("Invalid insurance type");
                        }
                        break;
                    case 3:
                        if (loggedAccount instanceof Individual) {
                            // Implement method to buy a property insurance :
                            // account.buyInsurance(propertyInsurance);
                        } else {
                            System.out.println("Invalid insurance type");
                        }
                        break;
                    case 4:
                        if (loggedAccount instanceof Individual) {
                            return;
                        }
                    default:
                        System.out.println("Invalid insurance type");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    // Cancel insurance
    public void cancelInsurance(Account loggedAccount) {

        // Check if the account has already insurances
        List<Insurance> insurances = loggedAccount.getInsurances();
        if (insurances.isEmpty()) {
            System.out.println("No insurance found.");
            return;
        }

        while (true) {
            System.out.println("Insurances:");
            for (int i = 0; i < insurances.size(); i++) {
                System.out.println((i + 1) + ". " + insurances.get(i));
            }
            System.out.println("Which insurance do you want to cancel? ");

            try {
                int index = scanner.nextInt();
                if (index >= 1 && index <= insurances.size()) {
                    Insurance selectedInsurance = insurances.get(index - 1);
                    loggedAccount.removeInsurance(selectedInsurance);
                    return;
                } else {
                    System.out.println("Invalid insurance selection. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }

    // Log in and authenticate the account
    private Account login(String email, String password) {
        try {
            for (Account account : accounts) {
                User user = account.getUser();
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    account.setAuthenticationStatus(AuthenticationStatus.SUCCESS);
                    account.getUser().setLastLoginDate();
                    System.out.println("Login successful!");
                    return account;
                }
            }
            throw new InvalidAuthenticationException("Invalid email or password.");
        } catch (InvalidAuthenticationException e) {
            System.out.println("Invalid email or password. Please try again.");
            return null;
        }
    }

    static class InvalidAuthenticationException extends Exception {
        public InvalidAuthenticationException(String message) {
            super(message);
        }
    }

    // Get input parameters from the user and ensure they are not empty
    private String getInput(String prompt) {
        String input = "";
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Field is required.");
            }
        } while (input.isEmpty());
        return input;
    }

}
