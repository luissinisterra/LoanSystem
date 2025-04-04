package app.model;

import java.util.HashMap;
import java.util.Map;

public class Client extends Person {
    private Map<String, Loan> loans;

    public Client(String id, String firstName, String secondName, String firstSurname, String secondSurname, int age, String email, String phone, Address address) {
        super(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, address);
        this.loans = new HashMap<>();
    }

    public Map<String, Loan> getLoans() {
        return loans;
    }

    public void setLoans(Map<String, Loan> loans) {
        this.loans = loans;
    }
}
