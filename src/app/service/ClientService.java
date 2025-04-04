package app.service;

import app.service.imp.IClientService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {
    private String BASE_URL = "localhost:8080";
    private IClientService iClientService;

    private void setBaseURL() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iClientService = retrofit.create(IClientService.class);
    }


    /*private List<Clients> listarTodosUsuarios() {
        try {
            Response<List<Usuario>> response = apiService.getAllUsuarios().execute();
            if (response.isSuccessful()) {
                List<Usuario> usuarios = response.body();
                usuarios.forEach(usuario -> System.out.println(usuario));
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
