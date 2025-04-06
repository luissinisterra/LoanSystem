package app.model;

import java.time.LocalDate;


public class Loan {
    private String id;
    private Client client;
    private double amount;
    private boolean active;
    private LocalDate date;

    public Loan(String id, Client client, double amount, LocalDate date) {
        this.id = id;
        this.client = client;
        this.amount = amount;
        this.date = date;
        this.active = true;
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
