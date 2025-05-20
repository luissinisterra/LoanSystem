package app.service;

import app.dto.CreateOverheadDTO;
import app.dto.OverheadResponseDTO;
import app.exception.ApiException;
import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Overhead;
import app.service.imp.IOverheadService;
import app.util.ApiErrorUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.CellRange;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GastoService {
    private IOverheadService apiService;

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
        apiService = retrofit.create(IOverheadService.class);
    }

    public void remove(Integer id) {
        try {
            Response<Void> response = apiService.deleteGasto(id).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
        } catch (IOException e) {
            throw new ApiException("Error de conexión al eliminar un gasto");
        }
    }

    public OverheadResponseDTO add(Integer userId, String overheadType, String overheadDescription, Integer ammount) {
        CreateOverheadDTO overhead = new CreateOverheadDTO(userId, overheadType, overheadDescription, ammount);
        try {
            Response<OverheadResponseDTO> response = apiService.createGasto(overhead).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al agregar un gasto");
        }
    }

    public OverheadResponseDTO getById(Integer id) {
        try {
            Response<OverheadResponseDTO> response = apiService.getGastoById(id).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener un gasto por ID");
        }
    }

    public OverheadResponseDTO update(Integer userId, String overheadType, String overheadDescription, Integer ammount, Integer id) {
        CreateOverheadDTO overhead = new CreateOverheadDTO(userId, overheadType, overheadDescription, ammount);
        try {
            Response<OverheadResponseDTO> response = apiService.updateGasto(id, overhead).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al actualizar gasto");
        }
    }

    public List<OverheadResponseDTO> getByUserID(Integer userID){
        try {
            Response<List<OverheadResponseDTO>> response = apiService.getOverheadByUserId(userID).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener gastos");
        }
    }
}
