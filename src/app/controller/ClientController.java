package app.controller;

import app.model.Client;
import app.service.ClientService;
import retrofit2.http.Path;

import java.util.List;

public class ClientController {
    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    public List<Client> getAllClients() {
        return this.clientService.getAllClients();
    }

    public void createClient(Client client) {
        this.clientService.createClient(client);
    }

    public void updateClient(String id, Client client) {
        this.clientService.updateClient(id, client);
    }

    public void deleteClient(String id) {
        this.clientService.deleteClient(id);
    }

    public List<Client> searchClientsByQuery(String query) {
        return this.clientService.searchClientsByQuery(query);
    }

}
