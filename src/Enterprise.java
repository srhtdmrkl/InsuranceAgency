import java.util.Scanner;

public class Enterprise extends Account {

    private Scanner scanner = new Scanner(System.in);
    private String company;
    private Integer averageAge;
    private Integer employeeCount;
    private Integer smokersCount;

    public Enterprise(User user, String company) {
        super(user);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public void buyInsurance(Insurance insurance) {
        if (employeeCount == null) {
            while (true) {
                System.out.println("How many employees will the insurance cover? ");
                int employeeCount = scanner.nextInt();
                this.employeeCount = employeeCount;
                break;
            }
        }
        if (averageAge == null) {
            while(true) {
                System.out.println("What is the average age of the employee? ");
                int averageAge = scanner.nextInt();
                this.averageAge = averageAge;
                break;
            }
        }
        if (smokersCount == null) {
            while(true) {
                System.out.println("What is the number of smoker employees? ");
                int smokersCount = scanner.nextInt();
                this.smokersCount = smokersCount;
                break;
            }
        }
        System.out.println("Premium: " + insurance.calculate(this) + "per year");
        System.out.println("Are you sure you want to buy? [Y]es / [N]o");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            insurances.add(insurance);
            System.out.println("Health insurance purchased successfully!");
        } else if (choice.equalsIgnoreCase("N")) {
            System.out.println("Cancelled health insurance purchase.");
            return;
        }
    }

    @Override
    public int compareTo(Account other) {
        return this.getUser().getEmail().compareTo(other.getUser().getEmail());
    }    
    
}