package app.controller;

import app.model.Loan;
import app.service.LoanService;

import java.util.List;

public class LoanController {
    private LoanService loanService;

    public LoanController() {
        this.loanService = new LoanService();
    }

    // Obtener todos los préstamos
    public List<Loan> getAllLoans() {
        return this.loanService.getAllLoans();
    }

    // Obtener un préstamo por ID
    public Loan getLoanById(String id) {
        return this.loanService.getLoanById(id);
    }

    // Crear un nuevo préstamo
    public void createLoan(Loan loan) {
        this.loanService.createLoan(loan);
    }

    // Actualizar un préstamo existente
    public void updateLoan(String id, Loan loan) {
        this.loanService.updateLoan(id, loan);
    }

    // Eliminar un préstamo
    public void deleteLoan(String id) {
        this.loanService.deleteLoan(id);
    }

    // Buscar préstamos por consulta
    public List<Loan> searchLoansByQuery(String query) {
        return this.loanService.searchLoansByQuery(query);
    }

    // Contar préstamos activos
    public int getActiveLoansCount() {
        return this.loanService.getActiveLoansCount();
    }
}