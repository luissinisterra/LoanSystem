package app.service.imp;

import app.model.Client;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IClientService {
    @GET("/api/clients")
    Call<List<Client>> getAllClients();

    @GET("/api/clients/{id}")
    Call<Client> getClientById(@Path("id") String id);

    @POST("/api/clients")
    Call<Void> createClient(@Body Client client);

    @PUT("/api/clients/{id}")
    Call<Void> updateClient(@Path("id") String id, @Body Client client);

    @DELETE("/api/clients/{id}")
    Call<Void> deleteClient(@Path("id") String id);

    @GET("/api/clients/search")
    Call<List<Client>> searchClientsByQuery(@Query("query") String query);
}
