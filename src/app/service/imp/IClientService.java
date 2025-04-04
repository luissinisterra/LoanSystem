package app.service.imp;

import app.model.Client;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface IClientService {
    @GET("/clients")
    Call<List<Client>> getAllClients();
}
