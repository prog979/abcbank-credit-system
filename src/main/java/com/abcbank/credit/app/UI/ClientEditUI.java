package com.abcbank.credit.app.UI;


import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.service.ClientService;
import com.abcbank.credit.app.service.CreditOfferService;
import com.vaadin.data.Binder;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


import java.sql.SQLException;

public class ClientEditUI extends VerticalLayout {
    private ClientService clientService = new ClientService();
    private TextField secnameField = new TextField("Фамилия");
    private TextField nameField = new TextField("Имя");
    private TextField patronymicField = new TextField("Отчество");
    private TextField pasport_numberField = new TextField("Номер паспорта");


    private TextField phone_numberField = new TextField("Номер телефона");
    private TextField email = new TextField("Электронная почта");

    private Button add = new Button("Добавить");
    private Button update = new Button("Изменить");
    private Button delete = new Button("Удалить");
    private Button cancel = new Button("Отмена");

    private Client client;
    private Binder<Client> binder = new Binder<>();
    private CreditOfferService creditOfferService = new CreditOfferService();

    public ClientEditUI(Client client, ClientView clientView) {
        this.client = client;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(clientView);
        layout.addComponents(add, update, delete, cancel);
        addComponents(secnameField, nameField, patronymicField, phone_numberField, email, pasport_numberField, layout);
    }

    public void editConfigure(Client client) {
        setVisible(true);
        if (client == null) {
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
        } else {
            this.client = client;
            setClient(client);
            add.setVisible(false);
            update.setVisible(true);
            delete.setVisible(true);
            delete.setComponentError(null);

        }
        secnameField.setPlaceholder("Введите фамилию");
        nameField.setPlaceholder("Введите имя");
        patronymicField.setPlaceholder("Введите отчество");
        phone_numberField.setPlaceholder("Введите номер телефона");
        phone_numberField.setWidth(8, Unit.EM);
        email.setPlaceholder("Введите почту");
        email.setWidth(14, Unit.EM);
        pasport_numberField.setPlaceholder("Введите номер паспорта");
        pasport_numberField.setWidth(8, Unit.EM);
    }

    private void clear() {
        secnameField.clear();
        nameField.clear();
        patronymicField.clear();
        phone_numberField.clear();
        email.clear();
        pasport_numberField.clear();
    }

    private void addClickListeners(ClientView clientView) {
        add.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    addClient();
                    clientView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                updateClient(client);
                clientView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        delete.addClickListener(event -> {
            try {
                if (creditOfferService.getCreditOfferByClient(client).isEmpty()) {
                    clientService.deleteClient(client);
                    clientView.updateGrid();
                    this.setVisible(false);
                    clear();
                } else {
                    delete.setComponentError(new UserError("У клиента есть действующие кредитные договоры"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck() {
        if (nameField.isEmpty() || secnameField.isEmpty() || patronymicField.isEmpty() || email.isEmpty()
                || phone_numberField.isEmpty() || pasport_numberField.isEmpty()) {
            add.setComponentError(new UserError("Все поля должны быть заполнены!"));
            return false;
        } else {
            add.setComponentError(null);
            return true;
        }
    }

    private void setClient(Client client) {
        secnameField.setValue(client.getSecname());
        nameField.setValue(client.getName());
        patronymicField.setValue(client.getPatronymic());
        phone_numberField.setValue(client.getPhoneNumber());
        email.setValue(client.getEmail());
        pasport_numberField.setValue(client.getPasportNumber());
    }

    private void addClient() throws SQLException {
        Client client = new Client(secnameField.getValue(),
                nameField.getValue(),
                patronymicField.getValue(),
                phone_numberField.getValue(),
                email.getValue(),
                pasport_numberField.getValue());
        clientService.addClient(client);
    }

    private void updateClient(Client client) throws SQLException {
        client.setSecname(secnameField.getValue());
        client.setName(nameField.getValue());
        client.setPatronymic(patronymicField.getValue());
        client.setPhoneNumber(phone_numberField.getValue());
        client.setEmail(email.getValue());
        client.setPasportNumber(pasport_numberField.getValue());
        clientService.updateClient(client);
    }
}
