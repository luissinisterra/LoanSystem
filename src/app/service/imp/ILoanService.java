package app.service.imp;

import app.model.Loan;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ILoanService {
    @GET("/api/loans")
    Call<List<Loan>> getAllLoans();

    @GET("/api/loans/{id}")
    Call<Loan> getLoanById(@Path("id") String id);

    @POST("/api/loans")
    Call<Void> createLoan(@Body Loan loan);

    @PUT("/api/loans/{id}")
    Call<Void> updateLoan(@Path("id") String id, @Body Loan loan);

    @DELETE("/api/loans/{id}")
    Call<Void> deleteLoan(@Path("id") String id);

    @GET("/api/loans/search")
    Call<List<Loan>> searchLoansByQuery(@Query("query") String query);
}