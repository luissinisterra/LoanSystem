package app.dto;

import java.time.LocalDate;

public class IncomeCreateDTO {

    private Integer userId;
    private String incomeType;
    private String incomeDescription;
    private Integer ammount;
    private LocalDate incomeDate = LocalDate.now();

    public IncomeCreateDTO(Integer ammount, String incomeDescription, String incomeType, Integer userId) {
        this.ammount = ammount;
        this.incomeDescription = incomeDescription;
        this.incomeType = incomeType;
        this.userId = userId;
    }

    // Getters y Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getAmmount() {
        return ammount;
    }

    public void setAmmount(Integer ammount) {
        this.ammount = ammount;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }
}
