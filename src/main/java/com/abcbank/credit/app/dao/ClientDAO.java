package com.abcbank.credit.app.dao;

import com.abcbank.credit.app.entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    public void addClient(Client client) throws SQLException;
    public void updateClient(Client client) throws SQLException;
    public Client getClientById(String id) throws SQLException;
    public List getAllClients() throws SQLException;
    public void deleteClient(Client client) throws SQLException;
    public List getClientsByFIO(String client) throws SQLException;
}
