package com.abcbank.credit.app.UI;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.entities.PaymentGraphic;
import com.abcbank.credit.app.service.ClientService;
import com.abcbank.credit.app.service.CreditOfferService;
import com.abcbank.credit.app.service.CreditService;
import com.abcbank.credit.app.service.PaymentGraphicService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditOfferEditUI extends VerticalLayout {
    private TextField creditSum = new TextField("Сумма кредита");
    private TextField monthsOfCredit = new TextField("Срок выплат (месяцы)");
    private ComboBox<Client> clientSelect = new ComboBox<>("Клиент");
    private ComboBox<Credit> creditSelect = new ComboBox<>("Кредит");
    private Label itog = new Label("");
    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Button addPaymentGraphic = new Button("Сформировать график платежей");
    private Button showPaymentGraphic = new Button("Посмотреть график платежей");
    private Button deletePaymentGraphic = new Button("Удалить график платежей");
    private Button calculateSum = new Button("Рассчитать итоговую сумму кредита");
    private CreditOffer creditOffer;
    private List<PaymentGraphic> paymentGraphicList;
    private ClientService clientService = new ClientService();
    private CreditService creditService = new CreditService();
    private CreditOfferService creditOfferService = new CreditOfferService();
    private PaymentGraphicService paymentGraphicService = new PaymentGraphicService();
    private CreditOfferView creditOfferView;
    private Binder<CreditOffer> binder = new Binder();

    private String MONTHS_OF_CREDIT_ERROR_MESSAGE = "Срок выплаты должен быть больше нуля";
    private String CREDIT_SUM_ERROR_MESSAGE = "Сумма должна быть больше нуля, и не выше лимита по кредиту";
    private String CREDIT_SUM_AND_LIMIT_LRROR_MESSAGE = "Сумма должна быть больше нуля, и не выше ";
    private String FIELD_ENTRY_ERROR_MESSAGE = "Поля введены неверно";


    public CreditOfferEditUI(CreditOffer creditOffer, CreditOfferView creditOfferView) throws SQLException {
        setVisible(false);
        if (creditOffer == null) {
            this.creditOffer = new CreditOffer();
        } else {
            this.creditOffer = creditOffer;
        }
        this.creditOfferView = creditOfferView;
        updateSelects();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout paymentLayout = new HorizontalLayout();
        HorizontalLayout serviceLayout = new HorizontalLayout();
        addClickListeners();
        layout.addComponents(add, update, delete, calculateSum, cancel);
        paymentLayout.addComponents(addPaymentGraphic, showPaymentGraphic);
        serviceLayout.addComponents(deletePaymentGraphic);
        addComponents(clientSelect, creditSelect, creditSum, monthsOfCredit, itog, layout, paymentLayout, serviceLayout);
    }


    public void editConfigure(CreditOffer creditOffer) {
        setVisible(true);
        try {
            updateSelects();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (creditOffer == null) {
            clear();
            update.setVisible(false);
            delete.setVisible(false);
            addPaymentGraphic.setVisible(false);
            deletePaymentGraphic.setVisible(false);
            showPaymentGraphic.setVisible(false);
            itog.setVisible(false);
            calculateSum.setVisible(true);
            add.setVisible(true);
            creditOfferView.paymentGrid.setVisible(false);
        } else {
            this.creditOffer = creditOffer;
            setCreditOffer(creditOffer);
            add.setVisible(false);
            update.setVisible(true);
            update.setComponentError(null);
            delete.setVisible(true);
            delete.setComponentError(null);
            calculateSum.setVisible(false);
            itog.setVisible(true);
            creditOfferView.paymentGrid.setVisible(false);
            calculateItog();
            try {
                if (paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer).isEmpty()) {
                    addPaymentGraphic.setVisible(true);
                    deletePaymentGraphic.setVisible(false);
                    showPaymentGraphic.setVisible(false);
                } else {
                    addPaymentGraphic.setVisible(false);
                    deletePaymentGraphic.setVisible(true);
                    deletePaymentGraphic.setComponentError(null);
                    showPaymentGraphic.setVisible(true);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        binder.forField(monthsOfCredit).withConverter(new StringToLongConverter(
                        MONTHS_OF_CREDIT_ERROR_MESSAGE))
                .bind(CreditOffer::getMonthsOfCredit, CreditOffer::setMonthsOfCredit);
        binder.forField(monthsOfCredit).withConverter(new StringToLongConverter(MONTHS_OF_CREDIT_ERROR_MESSAGE))
                .withValidator(event -> (event > 0), MONTHS_OF_CREDIT_ERROR_MESSAGE)
                .bind(CreditOffer::getMonthsOfCredit, CreditOffer::setMonthsOfCredit);
        creditSum.setPlaceholder("Введите сумму кредита");
        monthsOfCredit.setPlaceholder("Введите срок выплаты");
    }

    private void updateSelects() throws SQLException {
        clientSelect.setItems(clientService.getAllClients());
        clientSelect.setItemCaptionGenerator(Client::getInitials);
        creditSelect.setItems(creditService.getAllCredits());
        creditSelect.setItemCaptionGenerator(Credit::getCreditName);
        creditSelect.addValueChangeListener(valueChangeEvent -> {
            if (creditSelect.getValue() == null)
                binder.forField(creditSum).withConverter(new StringToLongConverter(
                                CREDIT_SUM_ERROR_MESSAGE))
                        .bind(CreditOffer::getCreditSum, CreditOffer::setCreditSum);
            else {
                binder.forField(creditSum).withConverter(new StringToLongConverter(
                                CREDIT_SUM_ERROR_MESSAGE))
                        .withValidator(event -> (event > 0 && event <= creditSelect.getValue().getCreditLimit()),
                                CREDIT_SUM_AND_LIMIT_LRROR_MESSAGE + creditSelect.getValue().getCreditLimit())
                        .bind(CreditOffer::getCreditSum, CreditOffer::setCreditSum);
            }
        });
    }

    private void addClickListeners() {
        calculateSum.addClickListener(clickEvent -> calculateItog());

        cancel.addClickListener(event -> {
            this.setVisible(false);
        });

        add.addClickListener(clickEvent -> {
            try {
                if (fieldCheck()) {
                    addCreditOffer();
                    creditOfferView.updateGrid();
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
                    if (!paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer).isEmpty())
                        removePaymentGraphic(creditOffer);
                    updateCreditOffer();
                    creditOfferView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        delete.addClickListener(event -> {
            try {
                if (!paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer).isEmpty())
                    removePaymentGraphic(creditOffer);
                deleteCreditOffer();
                creditOfferView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        addPaymentGraphic.addClickListener(event ->
        {
            try {
                if (paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer).isEmpty())
                    createPaymentGraphic();
                creditOfferView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        showPaymentGraphic.addClickListener(event -> {
            try {
                creditOfferView.paymentGridConfigure(paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        deletePaymentGraphic.addClickListener(event -> {
            try {
                if (!paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer).isEmpty()) {
                    removePaymentGraphic(creditOffer);
                    creditOfferView.updateGrid();
                    this.setVisible(false);
                    clear();
                } else {
                    deletePaymentGraphic.setComponentError(new UserError("По договору нет графика платежей"));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck() {
        try {
            if (creditSum.isEmpty()
                    || monthsOfCredit.isEmpty()
                    || clientSelect.getValue() == null
                    || creditSelect.getValue() == null
                    || Long.parseLong(creditSum.getValue()) <= 0
                    || Long.parseLong(creditSum.getValue()) > creditSelect.getValue().getCreditLimit()
                    || Long.parseLong(monthsOfCredit.getValue()) <= 0) {
                add.setComponentError(new UserError(FIELD_ENTRY_ERROR_MESSAGE));
                update.setComponentError(new UserError(FIELD_ENTRY_ERROR_MESSAGE));
                return false;
            }
        } catch (NumberFormatException ex) {
            add.setComponentError(new UserError(FIELD_ENTRY_ERROR_MESSAGE));
            update.setComponentError(new UserError(FIELD_ENTRY_ERROR_MESSAGE));
            return false;
        }
        add.setComponentError(null);
        update.setComponentError(null);
        return true;
    }

    private void calculateItog() {
        Long creditSum;
        Integer percents;
        try {
            creditSum = Long.parseLong(this.creditSum.getValue());
            percents = creditSelect.getValue().getPercent();
            Long creditBody = creditSum / 100 * (100 + percents);
            itog.setValue("Итоговая сумма кредита с процентами: " + creditBody + " ,  " +
                    "Сумма процентов: " + (creditBody - creditSum));
        } catch (Exception ex) {
            System.out.println("Error in calculateItog() method");
        }
        itog.setVisible(true);
    }

    private void createPaymentGraphic() {
        paymentGraphicList = new ArrayList<>();
        Long paimentRest;
        double creditMonhtPercent;
        Long creditMonhtPayment;

        creditMonhtPercent = (double) creditOffer.getCredit().getPercent() /
                (double) creditOffer.getMonthsOfCredit() / 100.0;
        creditMonhtPayment = (long) (Math.round(creditOffer.getCreditSum() *
                (creditMonhtPercent + creditMonhtPercent /
                        (Math.pow((1.0 + creditMonhtPercent), creditOffer.getMonthsOfCredit()) - 1.0))));

        paimentRest = creditOffer.getCreditSum();
        for (Long i = creditOffer.getMonthsOfCredit(), month = 1L; i > 0; i--, month++) {
            PaymentGraphic paymentGraphic = new PaymentGraphic();
            if (paimentRest < creditMonhtPayment || month == creditOffer.getMonthsOfCredit()) {
                creditMonhtPayment = Math.round(paimentRest + paimentRest * creditMonhtPercent);
            }
            paymentGraphic.setPaymentRest(paimentRest);
            paymentGraphic.setDate(LocalDate.now().plusMonths(month));
            paymentGraphic.setPaymentSum(creditMonhtPayment);
            paymentGraphic.setCreditPercents(Math.round(paymentGraphic.getPaymentRest() * creditMonhtPercent));
            paymentGraphic.setCreditBody(paymentGraphic.getPaymentSum() - paymentGraphic.getCreditPercents());
            paymentGraphic.setCreditOffer(creditOffer);
            paymentGraphic.setPaymentRest(paimentRest - paymentGraphic.getCreditBody());
            paimentRest -= paymentGraphic.getCreditBody();
            try {
//                System.out.println("График платежей сформирован");
                paymentGraphicService.addPaymentGraphic(paymentGraphic);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void addCreditOffer() throws SQLException {
        creditOffer = getCreditOffer();
        creditOfferService.addCreditOffer(creditOffer);
    }

    private void updateCreditOffer() throws SQLException {
        this.creditOffer = getCreditOffer();
        creditOfferService.updateCreditOffer(creditOffer);
    }

    private void deleteCreditOffer() throws SQLException {
        this.creditOffer = getCreditOffer();
        creditOfferService.deleteCreditOffer(creditOffer);
    }

    private CreditOffer getCreditOffer() {
        creditOffer.setCreditSum(Long.parseLong(creditSum.getValue()));
        creditOffer.setClient(clientSelect.getValue());
        creditOffer.setCredit(creditSelect.getValue());
        creditOffer.setMonthsOfCredit(Long.parseLong(monthsOfCredit.getValue()));
        return creditOffer;
    }

    private void setCreditOffer(CreditOffer creditOffer) {
        clientSelect.setSelectedItem(creditOffer.getClient());
        creditSelect.setSelectedItem(creditOffer.getCredit());
        creditSum.setValue(creditOffer.getCreditSum().toString());
        monthsOfCredit.setValue(creditOffer.getMonthsOfCredit().toString());
    }

    private void clear() {
        clientSelect.clear();
        creditSelect.clear();
        creditSum.clear();
        monthsOfCredit.clear();
        itog.setValue("");
    }

    private void removePaymentGraphic(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
        paymentGraphicList = new ArrayList<>();
        try {
            paymentGraphicList = paymentGraphicService.getPaymentGraphicByCreditOffer(creditOffer);
            for (PaymentGraphic pg : paymentGraphicList) {
                paymentGraphicService.deletePaymentGraphic(pg);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}