package app.service.imp;

import app.dto.IncomeCreateDTO;
import app.dto.IncomeResponseDTO;
import app.model.Income;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IIncomeService {

    // === Listar ===
    @GET("/api/incomes")
    Call<List<IncomeResponseDTO>> getIncomes(@Header("Authorization") String authHeader);

    @GET("/api/incomes/{id}")
    Call<IncomeResponseDTO> getIncomeByID(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    @GET("/api/incomes/user/{userId}")
    Call<List<IncomeResponseDTO>> getIncomesByUserID(
            @Header("Authorization") String authHeader,
            @Path("userId") Integer userID
    );

    // === Crear ===
    @POST("/api/incomes")
    Call<IncomeResponseDTO> addIncome(
            @Header("Authorization") String authHeader,
            @Body IncomeCreateDTO income
    );

    // === Actualizar ===
    @PUT("/api/incomes/{id}")
    Call<IncomeResponseDTO> updateIncome(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id,
            @Body IncomeCreateDTO income
    );

    // === Eliminar ===
    @DELETE("/api/incomes/{id}")
    Call<Void> deleteIncome(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );
}