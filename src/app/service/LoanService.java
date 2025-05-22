package app.service;

import app.exception.ApiException;
import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Loan;
import app.service.imp.ILoanService;
import app.util.ApiErrorUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            return loans;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Loan> getAllLoansByClientId(int id) {
        try{
            Response<List<Loan>> response = this.iLoanService.getAllLoansByClientId(id).execute();
            List<Loan> loans = response.body();

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            return loans;
        } catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Obtener todos los préstamos de un usuario en especifico
    public List<Loan> getAllLoansByUserId(int id) {
        try {
            Response<List<Loan>> response = this.iLoanService.getAllLoansByUserId(id).execute();
            List<Loan> userLoans = response.body();

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            return userLoans;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Obtener un préstamo por ID
    public Loan getLoanById(int id) {
        try {
            Response<Loan> response = this.iLoanService.getLoanById(id).execute();
            Loan loan = response.body();

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            return loan;
        } catch (IOException e) {
            throw new ApiException("Error de conexión");
        }
    }

    // Crear un nuevo préstamo
    public void createLoan(Loan loan) {
        try {
            Response<Void> response = this.iLoanService.createLoan(loan).execute();

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

        } catch (IOException e) {
            throw new ApiException("Error de conexión");
        }
    }

    // Actualizar un préstamo existente
    public void updateLoan(int id, Loan loan) {
        try {
            Response<Void> response = this.iLoanService.updateLoan(id, loan).execute();

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

        } catch (IOException e) {
            throw new ApiException("Error de conexión");
        }
    }

    // Eliminar un préstamo
    public void deleteLoan(int id) {
        try {
            Response<Void> response = this.iLoanService.deleteLoan(id).execute();

            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

        } catch (IOException e) {
            throw new ApiException("Error de conexión");
        }
    }

    // Buscar préstamos por consulta
    public List<Loan> searchLoansByQuery(int userId, String query) {
        try {
            Response<List<Loan>> response = this.iLoanService.searchLoansByQuery(userId, query).execute();
            List<Loan> loans = response.body();
            return loans;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
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
            return activeLoansCount;
        }
        return 0;
    }
}