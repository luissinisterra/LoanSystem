package app.service.imp;

import app.model.Income;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IIncomeService {

    //Listar
    @GET ("/api/incomes")
    Call<List<Income>> getIncomes();

    @GET ("/api/incomes/{id}")
    Call<Income> getIncomeByID(@Path("id") String id);

    //Crear
    @POST ("/api/incomes")
    Call<Income> addIncome(@Body Income i);

    //Actualizar
    @PUT ("/api/incomes/{id}")
    Call<Income>  updateIncome(@Path("id") String id, @Body Income i);

    //Eliminar
    @DELETE ("/api/incomes/{id}")
    Call<Void> deleteIncome(@Path("id") String id);

}
