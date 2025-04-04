package app.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;

public class DateTimeHelper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a", Locale.US);

    public static LocalDateTime parseDateTime(String dateString) {
        try {
            return LocalDateTime.parse(dateString, FORMATTER);
        } catch (Exception e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
            return null;
        }
    }

    public static String obtenerRangoFechas(String periodo) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio;

        switch (periodo) {
            case "1 Semana":
                inicio = hoy.minusWeeks(1);
                break;
            case "1 Mes":
                inicio = hoy.minusMonths(1);
                break;
            case "3 Meses":
                inicio = hoy.minusMonths(3);
                break;
            case "6 Meses":
                inicio = hoy.minusMonths(6);
                break;
            case "1 Año":
                inicio = hoy.minusYears(1);
                break;
            default:
                inicio = hoy;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return inicio.format(formatter) + " - " + "hoy";
    }

}