package app.service.imp;

import app.dto.IncomeCreateDTO;
import app.dto.IncomeResponseDTO;
import app.model.Income;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IIncomeService {

    //Listar
    @GET ("/api/incomes")
    Call<List<IncomeResponseDTO>> getIncomes();

    @GET ("/api/incomes/{id}")
    Call<IncomeResponseDTO> getIncomeByID(@Path("id") Integer id);

    @GET ("/api/incomes/user/{userId}")
    Call<List<IncomeResponseDTO>> getIncomesByUserID(@Path("userId") Integer userID);

    //Crear
    @POST ("/api/incomes")
    Call<IncomeResponseDTO> addIncome(@Body IncomeCreateDTO i);

    //Actualizar
    @PUT ("/api/incomes/{id}")
    Call<IncomeResponseDTO>  updateIncome(@Path("id") Integer id, @Body IncomeCreateDTO i);

    //Eliminar
    @DELETE ("/api/incomes/{id}")
    Call<Void> deleteIncome(@Path("id") Integer id);

}
