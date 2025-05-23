package app.service.imp;

import app.dto.CreateOverheadDTO;
import app.dto.OverheadResponseDTO;
import app.model.Overhead;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IOverheadService {

    // === Obtener por ID ===
    @GET("/api/overheads/{id}")
    Call<OverheadResponseDTO> getGastoById(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    // === Filtrar gastos ===
    @GET("/api/gastos/buscar")
    Call<List<Overhead>> buscarGastosPorFiltros(
            @Header("Authorization") String authHeader,
            @Query("tipoDeGasto") String tipoDeGasto,
            @Query("gastoMinimo") String gastoMinimo,
            @Query("gastoMaximo") String gastoMaximo,
            @Query("montoGasto") String montoGasto,
            @Query("filtroFecha") String filtroFecha
    );

    // === Crear gasto ===
    @POST("/api/overheads")
    Call<OverheadResponseDTO> createGasto(
            @Header("Authorization") String authHeader,
            @Body CreateOverheadDTO overhead
    );

    // === Eliminar gasto ===
    @DELETE("/api/overheads/{id}")
    Call<Void> deleteGasto(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id
    );

    // === Actualizar gasto ===
    @PUT("/api/overheads/{id}")
    Call<OverheadResponseDTO> updateGasto(
            @Header("Authorization") String authHeader,
            @Path("id") Integer id,
            @Body CreateOverheadDTO overhead
    );

    // === Obtener por User ID ===
    @GET("/api/overheads/user/{userId}")
    Call<List<OverheadResponseDTO>> getOverheadByUserId(
            @Header("Authorization") String authHeader,
            @Path("userId") Integer userId
    );
}