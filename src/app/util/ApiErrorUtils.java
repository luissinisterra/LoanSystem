package app.util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Response;

public class ApiErrorUtils {
    public static String extractErrorMessage(Response<?> response) {
        if (response.errorBody() != null) {
            try {
                Gson gson = new Gson();
                JsonObject errorJson = gson.fromJson(response.errorBody().charStream(), JsonObject.class);
                return errorJson.has("message") ? errorJson.get("message").getAsString() : "Error desconocido";
            } catch (Exception e) {
                return "Error al leer el mensaje de error";
            }
        }
        return "Error sin cuerpo";
    }
}
