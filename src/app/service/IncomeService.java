package app.service;

import app.dto.IncomeCreateDTO;
import app.dto.IncomeResponseDTO;
import app.exception.ApiException;
import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Income;
import app.service.imp.IIncomeService;
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

public class IncomeService {
    private IIncomeService apiService;

    public IncomeService() {
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
        apiService = retrofit.create(IIncomeService.class);
    }

    public List<IncomeResponseDTO> getIncomes() {
        try {
            Response<List<IncomeResponseDTO>> response = apiService.getIncomes().execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener ingresos");
        }
    }

    public void remove(Integer id) {
        try {
            Response<Void> response = apiService.deleteIncome(id).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
        } catch (IOException e) {
            throw new ApiException("Error de conexión al eliminar ingreso");
        }
    }

    public IncomeResponseDTO add(Integer ammount, String incomeDescription, String incomeType, Integer userId) {
        IncomeCreateDTO income = new IncomeCreateDTO(ammount, incomeDescription, incomeType, userId);
        try {
            Response<IncomeResponseDTO> response = apiService.addIncome(income).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al agregar ingreso");
        }
    }

    public IncomeResponseDTO getById(int id) {
        try {
            Response<IncomeResponseDTO> response = apiService.getIncomeByID(id).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener ingreso por ID");
        }
    }

    public IncomeResponseDTO update(Integer ammount, String incomeDescription, String incomeType, Integer userId, Integer incomeID) {
        IncomeCreateDTO income = new IncomeCreateDTO(ammount, incomeDescription, incomeType, userId);
        try {
            Response<IncomeResponseDTO> response = apiService.updateIncome(incomeID, income).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al actualizar ingreso");
        }
    }

    public List<IncomeResponseDTO> getIncomesByUserID(Integer userID) {
        try {
            Response<List<IncomeResponseDTO>> response = apiService.getIncomesByUserID(userID).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        } catch (IOException e) {
            throw new ApiException("Error de conexión al obtener ingresos");
        }
    }
}
