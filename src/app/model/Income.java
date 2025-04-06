package app.model;


import java.time.LocalDate;
import java.util.UUID;

public class Income {

    private String incomeID;
    private String incomeType;
    private String incomeDescription;
    private double incomeAmount;
    private LocalDate incomeDate;

    public Income() {
        this.incomeID = UUID.randomUUID().toString();
        this.incomeDate = LocalDate.now();
    }

    public Income(String incomeType, String incomeDescription, double incomeAmount) {
        this.incomeID = UUID.randomUUID().toString();
        this.incomeDate = LocalDate.now();
        this.incomeType = incomeType;
        this.incomeDescription = incomeDescription;
        this.incomeAmount = incomeAmount;
    }

    //Getters and setters


    public String getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(String incomeID) {
        this.incomeID = incomeID;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    @Override
    public String toString() {
        return "ID: '" + incomeID + "'\n" +
                "Tipo de gasto: '" + incomeType + "'\n" +
                "Descripción del gasto: '" + incomeDescription + "'\n" +
                "Valor del gasto: " + incomeAmount + "\n" +
                "Fecha de creación del gasto: " + incomeDate;
    }
}
