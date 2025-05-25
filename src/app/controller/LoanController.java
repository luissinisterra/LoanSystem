package app.controller;

import app.dto.UserResponseDTO;
import app.model.Loan;
import app.model.User;
import app.service.LoanService;

import java.util.List;

public class LoanController {
    private LoanService loanService;

    public LoanController() {
        this.loanService = new LoanService();
    }

    // Obtener todos los préstamos
    public List<Loan> getAllLoans(UserResponseDTO user) {
        return this.loanService.getAllLoans(user);
    }

    public List<Loan> getAllLoansByClientId(int id, UserResponseDTO user) {
        return this.loanService.getAllLoansByClientId(id, user);
    }

    // Obtener todos los préstamos de un usuario en especifico
    public List<Loan> getAllLoansByUserId(int id, UserResponseDTO user) {
        return this.loanService.getAllLoansByUserId(id, user);
    }

    // Obtener un préstamo por ID
    public Loan getLoanById(int id, UserResponseDTO user) {
        return this.loanService.getLoanById(id, user);
    }

    // Crear un nuevo préstamo
    public void createLoan(Loan loan, UserResponseDTO user) {
        this.loanService.createLoan(loan, user);
    }

    // Actualizar un préstamo existente
    public void updateLoan(int id, Loan loan, UserResponseDTO user) {
        this.loanService.updateLoan(id, loan, user);
    }

    // Eliminar un préstamo
    public void deleteLoan(int id, UserResponseDTO user) {
        this.loanService.deleteLoan(id, user);
    }

    // Buscar préstamos por consulta
    public List<Loan> searchLoansByQuery(int userId, String query, UserResponseDTO user) {
        return this.loanService.searchLoansByQuery(userId, query, user);
    }

    // Contar préstamos activos
    public int getActiveLoansCount(UserResponseDTO user) {
        return this.loanService.getActiveLoansCount(user);
    }
}