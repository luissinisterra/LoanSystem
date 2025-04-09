package app.service;

import app.exception.ApiException;
import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.User;
import app.service.imp.IUserService;
import app.util.ApiErrorUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class UserService {
    private String BASE_URL = "http://localhost:8080";
    private IUserService apiService;

    public UserService() {
        this.setBaseURL();
    }

    private void setBaseURL() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Registra el adaptador
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.apiService = retrofit.create(IUserService.class);
    }

    public User saveUser(String names, String surnames, String email, String password, String username, String gender) {
        try {
            User user = new User(names, surnames, email, password, username, gender);
            Response<User> response = apiService.createUser(user).execute();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return response.body();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public User loadUser(String username, String password) {
        try {
            Response<User> response = apiService.loadUser(username, password).execute();
            User user = response.body();
            if (!response.isSuccessful()) {
                throw new ApiException(ApiErrorUtils.extractErrorMessage(response));
            }
            return user;
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
