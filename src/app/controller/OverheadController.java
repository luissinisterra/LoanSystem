package app.controller;

import app.dto.OverheadResponseDTO;
import app.dto.UserResponseDTO;
import app.model.Overhead;
import app.model.User;
import app.service.GastoService;

import java.util.List;

public class OverheadController {
    private GastoService gastoService;

    public OverheadController() {
        this.gastoService = new GastoService();
    }

    public void removeGasto(Integer id, UserResponseDTO user){
        this.gastoService.remove(id, user);
    }

    public OverheadResponseDTO addGasto(Integer userId, String overheadType, String overheadDescription, Integer ammount, UserResponseDTO user){
        return this.gastoService.add(userId, overheadType, overheadDescription, ammount, user);
    }

    public OverheadResponseDTO getByID(Integer id, UserResponseDTO user){
        return this.gastoService.getById(id, user);
    }

    public OverheadResponseDTO updateGasto(Integer userId, String overheadType, String overheadDescription, Integer ammount, Integer id, UserResponseDTO user){
        return this.gastoService.update(userId, overheadType, overheadDescription, ammount, id, user);
    }

    public List<OverheadResponseDTO> getByUserID(Integer userID, UserResponseDTO user){
        return this.gastoService.getByUserID(userID, user);
    }
}
