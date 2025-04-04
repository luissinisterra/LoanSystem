package app.service;

import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Gasto;
import app.service.imp.ApiGastoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GastoService {
    private static final String BASE_URL = "http://localhost:8080";
    private static ApiGastoService apiService;
    private static Retrofit retrofit = null;

    public GastoService() {
        setBaseUrl();
    }

    private static void setBaseUrl() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Registra el adaptador
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiGastoService.class);
    }

    public List<Gasto> getGastos() {
        try {
            Response<List<Gasto>> response = apiService.getAllGastos().execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            } else {
                List<Gasto> gastos = response.body();
                return gastos;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove (String id){
        try {
            Response<Void> response = apiService.deleteGasto(id).execute();
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

    public Gasto add (String tipo, String descripcion, double valor){
        try {
            Gasto gasto = new Gasto(tipo, descripcion, valor);
            Response<Gasto> response = apiService.createGasto(gasto).execute();
            if (!response.isSuccessful()) {
                System.out.println("Error: " + response.code());
            }
            else {
                return gasto;
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
