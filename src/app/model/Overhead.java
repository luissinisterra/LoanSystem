package app.model;


import com.github.weisj.jsvg.nodes.Use;

import java.time.LocalDate;

public class Overhead {

    private Integer overheadID;
    private User user;
    private String overheadType;
    private String overheadDescription;
    private Integer ammount;
    private LocalDate fechaGasto;

    public Overhead(String overheadType, String overheadDescription, Integer ammount) {
        this.overheadType = overheadType;
        this.overheadDescription = overheadDescription;
        this.ammount = ammount;
    }

    public Integer getOverheadID() {
        return overheadID;
    }

    public void setOverheadID(Integer overheadID) {
        this.overheadID = overheadID;
    }

    public User getUserID() {
        return user;
    }

    public void setUserID(User userID) {
        this.user = userID;
    }

    public String getOverheadType() {
        return overheadType;
    }

    public void setOverheadType(String overheadType) {
        this.overheadType = overheadType;
    }

    public String getOverheadDescription() {
        return overheadDescription;
    }

    public void setOverheadDescription(String overheadDescription) {
        this.overheadDescription = overheadDescription;
    }

    public Integer getAmmount() {
        return ammount;
    }

    public void setAmmount(Integer ammount) {
        this.ammount = ammount;
    }

    public LocalDate getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(LocalDate fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    @Override
    public String toString() {
        return "ID: '" + overheadID + "'\n" +
                "Tipo de gasto: '" + overheadType + "'\n" +
                "Descripción del gasto: '" + overheadDescription + "'\n" +
                "Valor del gasto: " + ammount + "\n" +
                "Fecha de creación del gasto: " + fechaGasto;
    }

}

