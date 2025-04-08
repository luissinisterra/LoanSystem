package app.service;

import app.exception.ApiException;
import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Gasto;
import app.model.Income;
import app.service.imp.IGastoService;
import app.util.ApiErrorUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GastoService {
    private IGastoService apiService;

    public GastoService() {
        setBaseUrl();
    }

    private void setBaseUrl() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Registra el adaptador
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String BASE_URL = "http://localhost:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(IGastoService.class);
    }

    public List<Gasto>getGastos() {
        try {
            Response<List<Gasto>> response = apiService.getAllGastos().execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener gastos");
        }
    }

    public void remove(String id) {
        try {
            Response<Void> response = apiService.deleteGasto(id).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
        } catch (IOException e) {
            throw new ApiException("Error de conexión al eliminar un gasto");
        }
    }

    public Gasto add(String tipo, String descripcion, double valor) {
        Gasto gasto = buildGasto(tipo, descripcion, valor);
        try {
            Response<Gasto> response = apiService.createGasto(gasto).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al agregar un gasto");
        }
    }

    public Gasto getById(String id) {
        try {
            Response<Gasto> response = apiService.getGastoById(id).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener un gasto por ID");
        }
    }

    public Gasto update(String id, String tipo, String descripcion, double valor) {
        Gasto gasto = buildGasto(tipo, descripcion, valor);
        try {
            Response<Gasto> response = apiService.updateGasto(id, gasto).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al actualizar gasto");
        }
    }

    private Gasto buildGasto(String tipo, String descripcion, double valor) {
        return new Gasto(tipo, descripcion, valor);
    }
}
