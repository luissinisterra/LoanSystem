package app.service.imp;

import app.dto.LoginRequest;
import app.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface IUserService {

    // === Métodos protegidos con JWT ===

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @PUT("/api/users/{id}")
    Call<User> updateUser(
            @Header("Authorization") String authHeader,
            @Path("id") int id,
            @Body User user
    );

    @DELETE("/api/users/{id}")
    Call<Void> deleteUser(
            @Header("Authorization") String authHeader,
            @Path("id") int id
    );

    // === Método público: login ===

    @POST("/api/users/login")
    Call<User> loadUser(@Body LoginRequest loginRequest);
}