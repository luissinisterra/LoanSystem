package app.service.imp;

import app.dto.LoginRequest;
import app.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface IUserService {

    @POST("/api/users")
    Call<User> createUser(@Body User user);
    @GET("/api/users/login")
    Call<User> loadUser(@Body LoginRequest loginRequest);
}
