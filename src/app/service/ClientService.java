package app.service;

import app.exception.ApiException;
import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Client;
import app.model.Loan;
import app.model.User;
import app.service.imp.IClientService;
import app.util.ApiErrorUtils;
import com.google.gson.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Client> getAllClients(User user) {
        try {
            String token = "Bearer " + user.getToken();
            Response<List<Client>> response = this.iClientService.getAllClients(token).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            List<Client> clients = response.body();
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Client> getAllClientsByUserId(int id, User user) {
        try {
            String token = "Bearer " + user.getToken();
            Response<List<Client>> response = this.iClientService.getAllClientsByUserId(token, id).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            List<Client> clients = response.body();
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Client getClientById(int id, User user) {
        try{
            String token = "Bearer " + user.getToken();
            Response<Client> response = this.iClientService.getClientById(token, id).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            Client client = response.body();
            return client;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void createClient(Client client, User user) {
        try{
            String token = "Bearer " + user.getToken();
            Response<Void> response = this.iClientService.createClient(token, client).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateClient(int id, Client client, User user) {
        try {
            String token = "Bearer " + user.getToken();
            Response<Void> response = this.iClientService.updateClient(token, id, client).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int id, User user) {
        try {
            String token = "Bearer " + user.getToken();
            Response<Void> response = this.iClientService.deleteClient(token, id).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Client> searchClientsByQuery(int userId, String query, User user) {
        try {
            String token = "Bearer " + user.getToken();
            Response<List<Client>> response = this.iClientService.searchClientsByQuery(token, userId, query).execute();

            if(!response.isSuccessful()){
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }

            List<Client> clients = response.body();
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int getActiveClientsCount(User user) {
        int activeClientsCount = 0;
        List<Client> clients = getAllClients(user);
        if (clients != null) {
            for (Client client : clients) {
                if (client.isActive()){
                    activeClientsCount++;
                }
            }
            return activeClientsCount;
        }
        return 0;
    }

}