package app.controller;

import app.model.Gasto;
import app.service.GastoService;

import java.util.List;

public class GastoController {
    private GastoService gastoService;

    public GastoController() {
        this.gastoService = new GastoService();
    }

    public List<Gasto> getGastos(){
        return this.gastoService.getGastos();
    }

    public void removeGasto(String id){
        this.gastoService.remove(id);
    }
}
