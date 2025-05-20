package app.dto;
import app.model.Income;
import java.time.LocalDate;

public class IncomeResponseDTO {
    private Integer id;
    private Integer userId;
    private String incomeType;
    private String incomeDescription;
    private Integer ammount;
    private LocalDate incomeDate;

    public IncomeResponseDTO() {}

    public IncomeResponseDTO(Integer id, Integer userId, String incomeType, String incomeDescription, Integer ammount, LocalDate incomeDate) {
        this.id = id;
        this.userId = userId;
        this.incomeType = incomeType;
        this.incomeDescription = incomeDescription;
        this.ammount = ammount;
        this.incomeDate = incomeDate;
    }

    public IncomeResponseDTO(Income income) {
        this.id = income.getIncomeID();
        this.userId = income.getUserID().getId();
        this.incomeType = income.getIncomeType();
        this.incomeDescription = income.getIncomeDescription();
        this.ammount = income.getIncomeAmount();
        this.incomeDate = income.getIncomeDate();
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
