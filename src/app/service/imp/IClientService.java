package app.service.imp;

import app.model.Client;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface IClientService {

    @GET("/api/clients")
    Call<List<Client>> getAllClients(@Header("Authorization") String authHeader);

    @GET("/api/clients/user/{id}")
    Call<List<Client>> getAllClientsByUserId(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    @GET("/api/clients/{id}")
    Call<Client> getClientById(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    @POST("/api/clients")
    Call<Void> createClient(
            @Header("Authorization") String authHeader,
            @Body Client client
    );

    @PUT("/api/clients/{id}")
    Call<Void> updateClient(
            @Header("Authorization") String authHeader,
            @Path("id") int id,
            @Body Client client
    );

    @DELETE("/api/clients/{id}")
    Call<Void> deleteClient(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    @GET("/api/clients/search/{userId}")
    Call<List<Client>> searchClientsByQuery(
            @Header("Authorization") String authHeader,
            @Path("userId") int userId,
            @Query("query") String query
    );
}