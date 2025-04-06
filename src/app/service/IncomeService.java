package app.service;

import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Income;
import app.service.imp.IIncomeService;
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
    private static final String BASE_URL = "http://localhost:8080";
    private static IIncomeService apiService;

    public IncomeService() {
        setBaseUrl();
    }

    private void setBaseUrl() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Registra el adaptador
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(IIncomeService.class);
    }

    public List<Income> getIncomes() {
        try {
            Response<List<Income>> response = apiService.getIncomes().execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            } else {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove (String id){
        try {
            Response<Void> response = apiService.deleteIncome(id).execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            }
            else {
                System.out.println("Success");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Income add (String tipo, String descripcion, double valor){
        try {
            Income i = new Income(tipo, descripcion, valor);
            Response<Income> response = apiService.addIncome(i).execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            }
            else {
                return i;
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Income getById(String id){
        try {
            Response<Income> response = apiService.getIncomeByID(id).execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            }
            else {
                return response.body();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Income update(String id, String tipo, String descripcion, double valor) {
        try {
            Income i= new Income(tipo, descripcion, valor);
            Response<Income> response = apiService.updateIncome(id, i).execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            } else {
                return i;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
