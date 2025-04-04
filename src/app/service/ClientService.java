package app.service;

import app.model.Client;
import app.service.imp.IClientService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ClientService {
    private String BASE_URL = "localhost:8080";
    private IClientService iClientService;

    public ClientService() {
        this.setBaseURL();
    }

    private void setBaseURL() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iClientService = retrofit.create(IClientService.class);
    }

    public List<Client> getAllClients() {
        try {
            Response<List<Client>> response = this.iClientService.getAllClients().execute();
            if (response.isSuccessful()) {
                List<Client> clients = response.body();
                return clients;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
