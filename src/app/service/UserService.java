package app.service;

import app.helper.LocalDateAdapter;
import app.helper.LocalDateTimeAdapter;
import app.model.Client;
import app.model.Income;
import app.model.User;
import app.service.imp.IClientService;
import app.service.imp.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                System.out.println("Error: " + response.code());
            }
            else {
                return response.body();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
