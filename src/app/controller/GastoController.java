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

    public Gasto addGasto(String tipo, String descripcion, double valor){
        return this.gastoService.add(tipo, descripcion, valor);
    }

    public Gasto getByID(String id){
        return this.gastoService.getById(id);
    }

    public Gasto updateGasto(String id, String tipo, String descripcion, double valor){
        return this.gastoService.update(id, tipo, descripcion, valor);
    }
}
