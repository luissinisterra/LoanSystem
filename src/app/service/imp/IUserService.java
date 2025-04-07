package app.service.imp;

import app.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IUserService {

    @POST("/api/users")
    Call<User> createUser(@Body User user);
}
