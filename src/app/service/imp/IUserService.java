package app.service.imp;

import app.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface IUserService {

    @POST("/api/users")
    Call<User> createUser(@Body User user);
    @GET("/api/users/load/{username}/{password}")
    Call<User> loadUser(@Path("username") String username, @Path("password") String password);
}
