package app.controller;

import app.model.Client;
import app.model.Loan;
import app.model.User;
import app.service.ClientService;
import retrofit2.http.Path;

import java.util.List;

public class ClientController {
    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    public List<Client> getAllClients(User user) {
        return this.clientService.getAllClients(user);
    }

    public List<Client> getAllClientsByUserId(int id, User user) {
        return this.clientService.getAllClientsByUserId(id, user);
    }

    public Client getClientById(int id, User user) {
        return this.clientService.getClientById(id, User user);
    }

    public void createClient(Client client, User user) {
        this.clientService.createClient(client, user);
    }

    public void updateClient(int id, Client client, User user) {
        this.clientService.updateClient(id, client, user);
    }

    public void deleteClient(int id, User user) {
        this.clientService.deleteClient(id, user);
    }

    public List<Client> searchClientsByQuery(int userId, String query, User user) {
        return this.clientService.searchClientsByQuery(userId, query, user);
    }

    public int getActiveClientsCount() {
        return this.clientService.getActiveClientsCount();
    }
}
