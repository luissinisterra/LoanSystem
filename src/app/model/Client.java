package app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends Person {
    private boolean active;
    private List<Loan> loans;

    public Client(String id, String firstName, String secondName, String firstSurname, String secondSurname, int age, String email, String phone, Address address) {
        super(id, firstName, secondName, firstSurname, secondSurname, age, email, phone, address);
        this.active = true;
        this.loans = new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
