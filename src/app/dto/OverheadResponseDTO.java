package app.dto;

import app.model.Overhead;

import java.time.LocalDate;

public class OverheadResponseDTO {
    private Integer id;
    private Integer userId;
    private String overheadType;
    private String overheadDescription;
    private Integer ammount;
    private LocalDate overheadDate;

    public OverheadResponseDTO(Overhead overhead) {
        this.id = overhead.getOverheadID();
        this.userId = overhead.getUserID().getId();
        this.overheadType = overhead.getOverheadType();
        this.overheadDescription = overhead.getOverheadDescription();
        this.ammount = overhead.getAmmount();
        this.overheadDate = overhead.getFechaGasto();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public LocalDate getOverheadDate() {
        return overheadDate;
    }

    public void setOverheadDate(LocalDate overheadDate) {
        this.overheadDate = overheadDate;
    }
}
