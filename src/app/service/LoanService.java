package app.service;

import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Loan;
import app.service.imp.ILoanService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class LoanService {
    private String BASE_URL = "http://localhost:8080";
    private ILoanService iLoanService;
    private Retrofit retrofit;

    public LoanService() {
        this.setBaseURL();
    }

    // Configurar la URL base y el adaptador Gson
    private void setBaseURL() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Registra el adaptador
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.iLoanService = retrofit.create(ILoanService.class);
    }

    // Obtener todos los préstamos
    public List<Loan> getAllLoans() {
        try {
            Response<List<Loan>> response = this.iLoanService.getAllLoans().execute();
            List<Loan> loans = response.body();
            return loans;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtener un préstamo por ID
    public Loan getLoanById(String id) {
        try {
            Response<Loan> response = this.iLoanService.getLoanById(id).execute();
            Loan loan = response.body();
            return loan;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Crear un nuevo préstamo
    public void createLoan(Loan loan) {
        try {
            Response<Void> response = this.iLoanService.createLoan(loan).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Actualizar un préstamo existente
    public void updateLoan(String id, Loan loan) {
        try {
            Response<Void> response = this.iLoanService.updateLoan(id, loan).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un préstamo
    public void deleteLoan(String id) {
        try {
            Response<Void> response = this.iLoanService.deleteLoan(id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Buscar préstamos por consulta
    public List<Loan> searchLoansByQuery(String query) {
        try {
            Response<List<Loan>> response = this.iLoanService.searchLoansByQuery(query).execute();
            List<Loan> loans = response.body();
            return loans;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Contar préstamos activos
    public int getActiveLoansCount() {
        int activeLoansCount = 0;
        List<Loan> loans = getAllLoans();
        if (loans != null) {
            for (Loan loan : loans) {
                if (loan.isActive()) {
                    activeLoansCount++;
                }
            }
        }
        return activeLoansCount;
    }
}