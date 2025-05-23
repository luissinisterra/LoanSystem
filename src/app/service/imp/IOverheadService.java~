package app.service.imp;

import app.dto.CreateOverheadDTO;
import app.dto.OverheadResponseDTO;
import app.model.Overhead;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface IOverheadService {
    //Obtener por id
    @GET("/api/overheads/{id}")
    Call<OverheadResponseDTO> getGastoById(@Path("id") Integer id);

    //Filtrar
    @GET("/api/gastos/buscar")
    Call<List<Overhead>> buscarGastosPorFiltros(@Query("tipoDeGasto") String tipoDeGasto,
                                                @Query("gastoMinimo") String gastoMinimo,
                                                @Query("gastoMaximo") String gastoMaximo,
                                                @Query("montoGasto") String montoGasto,
                                                @Query("filtroFecha") String filtroFecha);
    //Post
    @POST("/api/overheads")
    Call<OverheadResponseDTO> createGasto(@Body CreateOverheadDTO overhead);

    //Remove
    @DELETE("/api/overheads/{id}")
    Call<Void> deleteGasto(@Path("id") Integer id);

    //Put
    @PUT("/api/overheads/{id}")
    Call<OverheadResponseDTO> updateGasto(@Path("id") Integer id, @Body CreateOverheadDTO overhead);

    @GET ("/api/overheads/user/{userId}")
    Call<List<OverheadResponseDTO>> getOverheadByUserId(@Path("userId") Integer userId);
}
