package app.service.imp;

import app.dto.LoginRequest;
import app.dto.UserResponseDTO;
import app.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface IUserService {

    // === Métodos protegidos con JWT ===

    @POST("/api/users")
    Call<UserResponseDTO> createUser(@Body User user);

    @PUT("/api/users/{id}")
    Call<UserResponseDTO> updateUser(
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
    Call<UserResponseDTO> loadUser(@Body LoginRequest loginRequest);
}