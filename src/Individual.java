
public class Individual extends Account {
    public Individual(User user) {
        super(user);
    }

    @Override
    public int compareTo(Account other) {
        return this.getUser().getEmail().compareTo(other.getUser().getEmail());
    }

}