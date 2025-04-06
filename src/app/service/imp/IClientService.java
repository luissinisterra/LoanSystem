package app.service.imp;

import app.model.Client;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IClientService {
    @GET("/clients")
    Call<List<Client>> getAllClients();

    @GET("/clients/{id}")
    Call<Client> getClientById(@Path("id") String id);

    @POST("/clients")
    Call<Void> createClient(@Body Client client);

    @PUT("/clients/{id}")
    Call<Void> updateClient(@Path("id") String id, @Body Client client);

    @DELETE("/clients/{id}")
    Call<Void> deleteClient(@Path("id") String id);

    @GET("/clients/search")
    Call<List<Client>> searchClientsByQuery(@Query("query") String query);
}
