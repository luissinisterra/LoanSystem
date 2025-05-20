package app.controller;

import app.dto.OverheadResponseDTO;
import app.model.Overhead;
import app.service.GastoService;

import java.util.List;

public class OverheadController {
    private GastoService gastoService;

    public OverheadController() {
        this.gastoService = new GastoService();
    }

    public void removeGasto(Integer id){
        this.gastoService.remove(id);
    }

    public OverheadResponseDTO addGasto(Integer userId, String overheadType, String overheadDescription, Integer ammount){
        return this.gastoService.add(userId, overheadType, overheadDescription, ammount);
    }

    public OverheadResponseDTO getByID(Integer id){
        return this.gastoService.getById(id);
    }

    public OverheadResponseDTO updateGasto(Integer userId, String overheadType, String overheadDescription, Integer ammount, Integer id){
        return this.gastoService.update(userId, overheadType, overheadDescription, ammount, id);
    }

    public List<OverheadResponseDTO> getByUserID(Integer userID){
        return this.gastoService.getByUserID(userID);
    }
}
