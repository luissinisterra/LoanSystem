package app.model;


import java.time.LocalDate;
import java.util.UUID;
public class Gasto {

    private String idGasto;
    private String tipoDeGasto;
    private String descripcionGasto;
    private double valorGasto;
    private LocalDate fechaGasto;

    public Gasto() {
        idGasto = UUID.randomUUID().toString();
        fechaGasto = LocalDate.now();
    }
    public Gasto (String tipoDeGasto, String descripcionGasto,  double valorGasto) {
        idGasto = UUID.randomUUID().toString();
        fechaGasto = LocalDate.now();
        this.tipoDeGasto = tipoDeGasto;
        this.descripcionGasto = descripcionGasto;
        this.valorGasto = valorGasto;
    }

    public double getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(double valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

    public String getTipoDeGasto() {
        return tipoDeGasto;
    }

    public void setTipoDeGasto(String tipoDeGasto) {
        this.tipoDeGasto = tipoDeGasto;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }
    public LocalDate getFechaGasto() {
        return fechaGasto;
    }
    public void setFechaGasto(LocalDate fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    @Override
    public String toString() {
        return "ID: '" + idGasto + "'\n" +
                "Tipo de gasto: '" + tipoDeGasto + "'\n" +
                "Descripción del gasto: '" + descripcionGasto + "'\n" +
                "Valor del gasto: " + valorGasto + "\n" +
                "Fecha de creación del gasto: " + fechaGasto;
    }

}

