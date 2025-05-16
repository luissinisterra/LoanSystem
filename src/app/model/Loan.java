package app.model;

import java.time.LocalDate;
import java.util.UUID;


public class Loan {
    private int id;
    private double amount;
    private double interestRate;
    private double term;
    private boolean active;
    private LocalDate date;
    private int clientId;
    private int userId;

    public Loan(double amount, double interestRate, double term, boolean active, LocalDate date, int clientId, int userId) {
        this.amount = amount;
        this.interestRate = interestRate;
        this.term = term;
        this.active = active;
        this.date = date;
        this.clientId = clientId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
