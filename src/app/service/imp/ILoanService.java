package app.service.imp;

import app.model.Loan;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ILoanService {
    @GET("/api/loans")
    Call<List<Loan>> getAllLoans();

    @GET("/api/loans/client-loans/{id}")
    Call<List<Loan>> getAllLoansByClientId(@Path("id") int id);

    @GET("/api/loans/user/{id}")
    Call<List<Loan>> getAllLoansByUserId(@Path("id") int id);

    @GET("/api/loans/{id}")
    Call<Loan> getLoanById(@Path("id") int id);

    @POST("/api/loans")
    Call<Void> createLoan(@Body Loan loan);

    @PUT("/api/loans/{id}")
    Call<Void> updateLoan(@Path("id") int id, @Body Loan loan);

    @DELETE("/api/loans/{id}")
    Call<Void> deleteLoan(@Path("id") int id);

    @GET("/api/loans/search/{userId}")
    Call<List<Loan>> searchLoansByQuery(@Path("userId") int userId, @Query("query") String query);
}