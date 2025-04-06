package app.controller;

import app.model.Income;
import app.service.IncomeService;

import java.util.List;

public class IncomeController {
    IncomeService incomeService;

    public IncomeController() {
        this.incomeService = new IncomeService();
    }

    public List<Income> getIncomes() {
        return incomeService.getIncomes();
    }

    public Income getIncomeByID(String id) {
        return incomeService.getById(id);
    }

    public void removeIncome(String id) {
        incomeService.remove(id);
    }

    public Income addIncome(String tipo, String descripcion, double valor) {
        return incomeService.add(tipo, descripcion, valor);
    }

    public Income updateIncome(String id, String tipo, String descripcion, double valor) {
        return incomeService.update(id, tipo, descripcion, valor);
    }

}
