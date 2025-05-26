package app.service.imp;

import app.model.Loan;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ILoanService {

    // === Listar préstamos ===
    @GET("/api/loans")
    Call<List<Loan>> getAllLoans(@Header("Authorization") String authHeader);

    @GET("/api/loans/client-loans/{id}")
    Call<List<Loan>> getAllLoansByClientId(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    @GET("/api/loans/user/{id}")
    Call<List<Loan>> getAllLoansByUserId(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    @GET("/api/loans/{id}")
    Call<Loan> getLoanById(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    // === Crear préstamo ===
    @POST("/api/loans")
    Call<Void> createLoan(
            @Header("Authorization") String authHeader,
            @Body Loan loan
    );

    // === Actualizar préstamo ===
    @PUT("/api/loans/{id}")
    Call<Void> updateLoan(
            @Header("Authorization") String authHeader,
            @Path("id") int id,
            @Body Loan loan
    );

    // === Eliminar préstamo ===
    @DELETE("/api/loans/{id}")
    Call<Void> deleteLoan(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    // === Búsqueda filtrada ===
    @GET("/api/loans/search/{userId}")
    Call<List<Loan>> searchLoansByQuery(
            @Header("Authorization") String authHeader,
            @Path("userId") int userId,
            @Query("query") String query
    );

    // === Búsqueda filtrada ===
    @GET("/api/loans/search/date/{userId}")
    Call<List<Loan>> searchLoansByDates(
            @Header("Authorization") String authHeader,
            @Path("userId") int userId,
            @Query("Date") String date
    );
}