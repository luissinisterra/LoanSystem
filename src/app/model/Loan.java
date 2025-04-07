package app.model;

import java.time.LocalDate;
import java.util.UUID;


public class Loan {
    private String id;
    private Client client;
    private double amount;
    private double interestRate;
    private double term;
    private boolean active;
    private LocalDate date;

    public Loan(){
        this.id = UUID.randomUUID().toString();
    }

    public Loan(Client client, double amount, double interestRate, double term, boolean active, LocalDate date) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.active = active;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
