package com.abcbank.credit.app.UI;

import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.service.CreditService;
import com.abcbank.credit.app.service.CreditOfferService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class CreditEditUI extends VerticalLayout {
    private TextField creditNameField = new TextField("Наименование кредита");
    private TextField creditPercent = new TextField("Процент по кредиту");
    private TextField creditLimit = new TextField("Лимит по кредиту");
    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Credit credit;
    private CreditService creditService = new CreditService();
    private CreditOfferService creditOfferService = new CreditOfferService();
    private Binder<Credit> binder = new Binder<>();

    private String FIELD_EMPTY_ERROR_MESSAGE = "Поле не введено";
    private String EMPTY_FIELD_ERROR_MESSAGE = "Поле должно быть заполнено";
    private String INCORRECT_FIELD_ENTERED_ERROR_MESSAGE = "Поле введено неверно";
    private String INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE = "Поля введены неверно";
    private String CREDIT_PERCENT_ERROR_MESSAGE = "Проценты должны быть больше 0 и меньше 100";
    private String CREDIT_LIMIT_ERROR_MESSAGE = "Лимит кредита должен быть больше нуля";
    private String DELETE_CREDIT_ERROR_MESSAGE = "По этому виду кредита есть действующие кредитные договоры";


    public CreditEditUI(Credit credit, CreditView creditView) {
        setVisible(false);
        setWidthUndefined();
        this.credit = credit;
        HorizontalLayout layout = new HorizontalLayout();
        addClickListeners(creditView);
        layout.addComponents(add, update, delete, cancel);
        addComponents(creditNameField, creditLimit, creditPercent, layout);
    }

    public void editConfigure(Credit credit) {
        binder.forField(creditNameField).withValidator(field -> field.length() > 0, EMPTY_FIELD_ERROR_MESSAGE)
                .bind(Credit::getCreditName, Credit::setCreditName);
        binder.forField(creditPercent).withValidator(field -> field.length() > 0,
                        EMPTY_FIELD_ERROR_MESSAGE)
                .withConverter(new StringToIntegerConverter(INCORRECT_FIELD_ENTERED_ERROR_MESSAGE))
                .withValidator(event -> (event > 0 && event <= 100), CREDIT_PERCENT_ERROR_MESSAGE)
                .bind(Credit::getPercent, Credit::setPercent);
        binder.forField(creditLimit).withValidator(field -> field.length() > 0,
                        FIELD_EMPTY_ERROR_MESSAGE)
                .withConverter(new StringToLongConverter(INCORRECT_FIELD_ENTERED_ERROR_MESSAGE))
                .withValidator(event -> (event > 0), CREDIT_LIMIT_ERROR_MESSAGE)
                .bind(Credit::getCreditLimit, Credit::setCreditLimit);
        setVisible(true);
        if (credit == null) {
            clear();
            update.setVisible(false);
            delete.setVisible(false);
            add.setVisible(true);
        } else {
            this.credit = credit;
            setCredit(credit);
            update.setVisible(true);
            update.setComponentError(null);
            delete.setVisible(true);
            delete.setComponentError(null);
            add.setVisible(false);
        }
        creditNameField.setPlaceholder("Введите название кредата");
        creditPercent.setPlaceholder("Введите процент по кредиту");
        creditPercent.setWidth(12, Unit.EM);
        creditLimit.setPlaceholder("Введите лимит по кредиту");
    }

    private void addClickListeners(CreditView creditView) {
        cancel.addClickListener(event -> this.setVisible(false));
        add.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    addCredit();
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    if (creditOfferExistChecker(credit)) {
                        updateCredit();
                        creditView.updateGrid();
                        this.setVisible(false);
                        clear();
                    } else update.setComponentError(new UserError(DELETE_CREDIT_ERROR_MESSAGE));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        delete.addClickListener(event -> {
            try {
                if (creditOfferExistChecker(credit)) {
                    creditService.deleteCredit(credit);
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                } else {
                    delete.setComponentError(new UserError(DELETE_CREDIT_ERROR_MESSAGE));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck() {
        if (creditNameField.isEmpty() || creditPercent.isEmpty() || creditLimit.isEmpty()) {
            add.setComponentError(new UserError(INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE));
            update.setComponentError(new UserError(INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE));
            return false;
        } else
            try {
                if (Integer.parseInt(creditPercent.getValue()) <= 0 || Integer.parseInt(creditLimit.getValue()) <= 0) {
                    add.setComponentError(new UserError(INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE));
                    update.setComponentError(new UserError(INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE));
                    return false;
                }
            } catch (NumberFormatException ex) {
                add.setComponentError(new UserError(INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE));
                update.setComponentError(new UserError(INCORRECT_FIELDS_ENTERED_ERROR_MESSAGE));
                return false;
            }
        add.setComponentError(null);
        update.setComponentError(null);
        return true;
    }

    private void addCredit() throws SQLException {
        creditService.addCredit(getCredit());
    }

    private void updateCredit() throws SQLException {
        creditService.updateCredit(getCredit());
    }

    private void clear() {
        creditNameField.clear();
        creditPercent.clear();
        creditLimit.clear();
    }

    private boolean creditOfferExistChecker(Credit credit) {
        this.credit = credit;
        try {
            if (creditOfferService.getCreditOfferByCredit(credit).isEmpty()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Credit getCredit() {
        credit.setCreditName(creditNameField.getValue());
        credit.setPercent(Integer.parseInt(creditPercent.getValue()));
        credit.setCreditLimit(Long.parseLong(creditLimit.getValue()));
        return credit;
    }

    private void setCredit(Credit credit) {
        creditNameField.setValue(credit.getCreditName());
        creditPercent.setValue(String.valueOf(credit.getPercent()));
        creditLimit.setValue(String.valueOf(credit.getCreditLimit()));
    }
}