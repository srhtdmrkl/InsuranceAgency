import java.time.LocalDate;

public class HealthInsurance extends Insurance {


    public HealthInsurance(LocalDate startDate) {
        super("Health Insurance", 500, startDate);
        this.endDate = startDate.plusMonths(12);
    }

    public double calculate(Account account) {
        if (account instanceof Individual) {
            premium *= 1.0;
        } else if (account instanceof Enterprise) {
            premium *= 0.6;
        }
        if (account.getUser().getAge() > 45) {
            premium *= 1.1;
        }
        if(account.getUser().isSmoker() == null) {
            System.out.println("Smoking status is required to calculate the premium.");
            account.getUser().smokerStaus();
        }
        if (account.getUser().isSmoker()) {
            premium *= 1.3;
        }
        return premium;
    }

}