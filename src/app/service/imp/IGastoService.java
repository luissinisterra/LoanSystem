package app.service.imp;

import app.model.Gasto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface IGastoService {

    //Listar
    @GET("/api/gastos")
    Call<List<Gasto>> getAllGastos();

    //Obtener por id
    @GET("/api/gastos/{id}")
    Call<Gasto> getGastoById(@Path("id") String id);

    //Filtrar
    @GET("/api/gastos/buscar")
    Call<List<Gasto>> buscarGastosPorFiltros(@Query("tipoDeGasto") String tipoDeGasto,
                                             @Query("gastoMinimo") String gastoMinimo,
                                             @Query("gastoMaximo") String gastoMaximo,
                                             @Query("montoGasto") String montoGasto,
                                             @Query("filtroFecha") String filtroFecha);
    //Post
    @POST("/api/gastos")
    Call<Gasto> createGasto(@Body Gasto gasto);

    //Remove
    @DELETE("/api/gastos/{id}")
    Call<Void> deleteGasto(@Path("id") String id);

    //Put
    @PUT("/api/gastos/{id}")
    Call<Gasto> updateGasto(@Path("id") String id, @Body Gasto gasto);
}
