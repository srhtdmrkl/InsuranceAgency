import java.time.LocalDate;

public abstract class Insurance {

    protected String name;
    protected double premium;
    protected LocalDate startDate;
    protected LocalDate endDate;

    public Insurance(String name, double premium, LocalDate startDate) {
        this.name = name;
        this.premium = premium;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(12);
    }

    public abstract double calculate(Account account);

    public void setPremium(double premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "Policy : " + name +  "\t Premium : $" + premium +  "\t Start Date : " + startDate + "\t End Date : " + endDate + "\n";
    }


    public class PropertyInsurance extends Insurance {
        private double houseValue;
        private double deductible;
    
        public PropertyInsurance(double basePremium, LocalDate startDate, double houseValue,
                double deductible) {
            super("Property Insurance", basePremium, startDate);
            this.houseValue = houseValue;
            this.deductible = deductible;
        }
    
        @Override
        public double calculate(Account account) {
            double premium = this.premium * houseValue / 1000;
            double riskFactor = 1.0;
            if (deductible > houseValue * 0.1) {
                riskFactor *= 1.5;
            }
            return premium * riskFactor;
        }
    }

    public class TravelInsurance extends Insurance {
        private String destination;

        public TravelInsurance(double premium, LocalDate startDate, String destination) {
            super("Travel Insurance", premium, startDate);
            this.destination = destination;
        }

        @Override
        public double calculate(Account account) {
            double premium = this.premium;
            if (!destination.equals("Turkiye")) {
                premium *= 1.5;
            }
            return premium;

        }
    }
}
