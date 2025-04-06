package app.service;

import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Client;
import app.service.imp.IClientService;
import com.google.gson.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClientService {
    private String BASE_URL = "http://localhost:8080";
    private IClientService iClientService;
    private Retrofit retrofit;

    public ClientService() {
        this.setBaseURL();
    }

    private void setBaseURL() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Registra el adaptador
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.iClientService = retrofit.create(IClientService.class);
    }

    public List<Client> getAllClients() {
        try {
            Response<List<Client>> response = this.iClientService.getAllClients().execute();
            List<Client> clients = response.body();
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Client getClientById(String id) {
        try{
            Response<Client> response = this.iClientService.getClientById(id).execute();
            Client client = response.body();
            return client;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void createClient(Client client) {
        try{
            Response<Void> response = this.iClientService.createClient(client).execute();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateClient(String id, Client client) {
        try {
            Response<Void> response = this.iClientService.updateClient(id, client).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(String id) {
        try {
            Response<Void> response = this.iClientService.deleteClient(id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Client> searchClientsByQuery(String query) {
        try {
            Response<List<Client>> response = this.iClientService.searchClientsByQuery(query).execute();
            List<Client> clients = response.body();
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}