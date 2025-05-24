package app.controller;

import app.dto.IncomeResponseDTO;
import app.dto.UserResponseDTO;
import app.model.Income;
import app.model.User;
import app.service.IncomeService;

import java.util.List;

public class IncomeController {
    IncomeService incomeService;

    public IncomeController() {
        this.incomeService = new IncomeService();
    }

    public List<IncomeResponseDTO> getIncomes(UserResponseDTO user) {
        return incomeService.getIncomes(user);
    }

    public IncomeResponseDTO getIncomeByID(Integer id, UserResponseDTO user) {
        return incomeService.getById(id, user);
    }

    public void removeIncome(Integer id, UserResponseDTO user) {
        incomeService.remove(id, user);
    }

    public IncomeResponseDTO addIncome(Integer ammount, String incomeDescription, String incomeType, Integer userId, UserResponseDTO user) {
        return incomeService.add(ammount, incomeDescription, incomeType, userId, user);
    }

    public IncomeResponseDTO updateIncome(Integer ammount, String incomeDescription, String incomeType, Integer userId, Integer incomeID, UserResponseDTO user) {
        return incomeService.update(ammount, incomeDescription, incomeType, userId, incomeID, user);
    }

    public List<IncomeResponseDTO> getIncomesByUserID(Integer userID, UserResponseDTO user) {
        return incomeService.getIncomesByUserID(userID, user);
    }

}
