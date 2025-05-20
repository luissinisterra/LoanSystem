package app.controller;

import app.model.Client;
import app.model.Loan;
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

    public List<Client> getAllClientsByUserId(int id) {
        return this.clientService.getAllClientsByUserId(id);
    }

    public List<Loan> getAllLoansByUserId(int id) {
        return this.clientService.getAllLoansByClientId(id);
    }

    public Client getClientById(int id) {
        return this.clientService.getClientById(id);
    }

    public void createClient(Client client) {
        this.clientService.createClient(client);
    }

    public void updateClient(int id, Client client) {
        this.clientService.updateClient(id, client);
    }

    public void deleteClient(int id) {
        this.clientService.deleteClient(id);
    }

    public List<Client> searchClientsByQuery(String query) {
        return this.clientService.searchClientsByQuery(query);
    }

    public int getActiveClientsCount() {
        return this.clientService.getActiveClientsCount();
    }
}
