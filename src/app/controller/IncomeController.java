package app.controller;

import app.dto.IncomeResponseDTO;
import app.model.Income;
import app.service.IncomeService;

import java.util.List;

public class IncomeController {
    IncomeService incomeService;

    public IncomeController() {
        this.incomeService = new IncomeService();
    }

    public List<IncomeResponseDTO> getIncomes() {
        return incomeService.getIncomes();
    }

    public IncomeResponseDTO getIncomeByID(Integer id) {
        return incomeService.getById(id);
    }

    public void removeIncome(Integer id) {
        incomeService.remove(id);
    }

    public IncomeResponseDTO addIncome(Integer ammount, String incomeDescription, String incomeType, Integer userId) {
        return incomeService.add(ammount, incomeDescription, incomeType, userId);
    }

    public IncomeResponseDTO updateIncome(Integer ammount, String incomeDescription, String incomeType, Integer userId, Integer incomeID) {
        return incomeService.update(ammount, incomeDescription, incomeType, userId, incomeID);
    }

    public List<IncomeResponseDTO> getIncomesByUserID(Integer userID) {
        return incomeService.getIncomesByUserID(userID);
    }

}
