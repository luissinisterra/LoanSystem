package app.controller;

import app.model.Client;
import app.service.ClientService;

import java.util.List;

public class ClientController {
    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    public List<Client> getAllClients() {
        return this.clientService.getAllClients();
    }
}
