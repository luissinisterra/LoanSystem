package app.dto;

import java.time.LocalDate;

public class CreateOverheadDTO {
    private Integer userId;
    private String overheadType;
    private String overheadDescription;
    private Integer ammount;
    private LocalDate overheadDate = LocalDate.now();

    public CreateOverheadDTO(Integer userId, String overheadType, String overheadDescription, Integer ammount) {
        this.userId = userId;
        this.overheadType = overheadType;
        this.overheadDescription = overheadDescription;
        this.ammount = ammount;
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
