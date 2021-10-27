package com.abcbank.credit.app.UI;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.service.ClientService;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.hibernate.event.spi.EventType;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ClientView extends VerticalLayout {
    private Grid<Client> clientGrid = new Grid<>(Client.class);
    private TextField filter = new TextField();
    private Button add = new Button("Новый");
    private ClientService clientService = new ClientService();
    private ClientEditUI addUI = new ClientEditUI(new Client(), this);

    public ClientView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(clientGrid, addUI);
        gridLayout.setExpandRatio(clientGrid, 1);
        filterConfigure();
        filter.setPlaceholder("Найти...");
        layout.addComponents(filter, add);
        add.addClickListener(clickEvent -> {
            addUI.setVisible(true);
            addUI.editConfigure(null);
        });
        addComponents(layout, gridLayout);
    }

    private void filterConfigure() {
        filter.addValueChangeListener(e -> {
            try {
                updateList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        filter.setValueChangeMode(ValueChangeMode.LAZY);
    }

    protected void updateList() throws SQLException {
        List<Client> clients = clientService.getClientsByFIO(filter.getValue());
        clientGrid.setItems(clients);
    }

    public void updateGrid() throws SQLException {
        clientGrid.setItems(clientService.getAllClients());
    }

    private void gridConfigure() throws SQLException {
        clientGrid.setWidth("900");
        clientService.fillClientTable();
        clientGrid.removeColumn("id");
        clientGrid.removeColumn("creditOffers");
//		clientGrid.setColumns("secname", "name", "patronymic", "phoneNumber", "email", "pasportNumber");
        clientGrid.setColumns();
        clientGrid.addColumn(Client::getSecname).setCaption("Фамилия");
        clientGrid.addColumn(Client::getName).setCaption("Имя");
        clientGrid.addColumn(Client::getPatronymic).setCaption("Отчество");
        clientGrid.addColumn(Client::getPhoneNumber).setCaption("Номер телефона");

        clientGrid.addColumn(Client::getEmail).setCaption("E-mail");

        List<Client> clients = clientService.getAllClients();
        clientGrid.setItems(clients);
        clientGrid.asSingleSelect().addSingleSelectionListener(event -> addUI.editConfigure(event.getValue()));
    }
}
