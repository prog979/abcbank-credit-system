package com.abcbank.credit.app.service;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.hibernate.Factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ClientService {
    private Factory factory = Factory.getInstance();

    public void fillClientTable() throws SQLException{

        if (getAllClients().isEmpty()) {
            List<Client> clients = addClients();
            clients.forEach(client -> {
                try {
                    factory.getClientDAO().addClient(client);
                } catch (SQLException ex){
                    System.out.println("Unable to populate Clients table");
                    ex.printStackTrace();
                } });
        }


    }

    private List<Client> addClients(){
        List<Client> clients = new ArrayList<>();
        clients.add(new Client("Петров", "Пётр", "Петрович", "89153456185",
                "petrovich@ya.ru", "4234 132456" ));
        clients.add(new Client("Семёнов", "Сидор", "Спиридонович", "89084531895"
                , "sidory-etomne@mail.ru", "4523 167890"));
        clients.add(new Client("Светлова", "Светлана", "Сергеевна", "89364567813",
                "svetlena@gmail.com", "3408 456783"));
        clients.add(new Client("Гребницкий", "Виктор", "Моисеевич", "89389137890",
                "rvm@gmail.com", "4234 7895435"));
        return clients;
    }

    public void addClient(Client client) throws SQLException {
        factory.getClientDAO().addClient(client);
    }

    public void updateClient(Client client) throws SQLException {
        factory.getClientDAO().updateClient(client);
    }

    public void deleteClient(Client client) throws SQLException {
        factory.getClientDAO().deleteClient(client);
    }

    public List getAllClients() throws SQLException {
        return factory.getClientDAO().getAllClients();
    }

    public Client getClientById(String id) throws SQLException {
        return factory.getClientDAO().getClientById(id);
    }

    public List getClientsByFIO(String fio) throws SQLException {
        if (fio == null){
            return getAllClients();
        } else return factory.getClientDAO().getClientsByFIO(fio);
    }
}
