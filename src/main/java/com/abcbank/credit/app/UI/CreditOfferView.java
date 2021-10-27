package com.abcbank.credit.app.UI;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.entities.PaymentGraphic;
import com.abcbank.credit.app.service.CreditOfferService;
import com.abcbank.credit.app.service.CreditService;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class CreditOfferView extends VerticalLayout {
    private Grid<CreditOffer> creditGrid = new Grid<>(CreditOffer.class);
    public Grid<PaymentGraphic> paymentGrid = new Grid<>(PaymentGraphic.class);
    private TextField filter = new TextField();
    private Button add = new Button("Новый");
    private CreditOfferService creditOfferService = new CreditOfferService();
    private CreditOfferEditUI addUI = new CreditOfferEditUI(new CreditOffer(), this);

    public CreditOfferView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(creditGrid, addUI);
        paymentGrid.setVisible(false);
        filterConfigure();                       //
        filter.setPlaceholder("Найти...");       //
        layout.addComponents(add);
        add.addClickListener(clickEvent -> {
            addUI.setVisible(true);
            addUI.editConfigure(null);
        });
        addComponents(layout, gridLayout, paymentGrid);
    }
// added
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
// added

    protected void updateList() throws SQLException {
      List<CreditOffer> creditOffers = creditOfferService.getCreditOfferByCredit(null);


        creditGrid.setItems(creditOffers);
    }

    public void updateGrid() throws SQLException {
        creditGrid.setItems(creditOfferService.getAllCreditOffers());
    }

    public void paymentGridConfigure(List<PaymentGraphic> paymentGraphics) throws SQLException {
        paymentGrid.setWidth("800");
        paymentGrid.setVisible(true);
        paymentGrid.setItems(paymentGraphics);
//      	paymentGrid.removeColumn("id");
//        paymentGrid.removeColumn("date");
//        paymentGrid.removeColumn("creditOffer");
//        paymentGrid.removeColumn("origDate");
        paymentGrid.setColumns();
        paymentGrid.addColumn(column -> column.getOrigDate().toString()).setCaption("Дата");
        paymentGrid.addColumn(PaymentGraphic::getPaymentSum).setCaption("Сумма платежа");
        paymentGrid.addColumn(PaymentGraphic::getCreditBody).setCaption("Тело кредита");
        paymentGrid.addColumn(PaymentGraphic::getCreditPercents).setCaption("Проценты");
        paymentGrid.addColumn(PaymentGraphic::getPaymentRest).setCaption("Остаток по кредиту");
//        paymentGrid.setColumns("paymentSum", "creditBody", "creditPercents", "paymentRest");


    }

    private void gridConfigure() throws SQLException {
        creditGrid.setWidth("800");
        creditGrid.removeColumn("client");
        creditGrid.removeColumn("credit");
//        creditGrid.setColumns("creditSum", "monthsOfCredit");
        creditGrid.setColumns();
        creditGrid.addColumn(client -> client.getClient().getInitials()).setCaption("Клиент");
        creditGrid.addColumn(CreditOffer::getCreditSum).setCaption("Сумма договора");
        creditGrid.addColumn(CreditOffer::getMonthsOfCredit).setCaption("Процент");
        creditGrid.addColumn(credit -> credit.getCredit().getCreditName()).setCaption("Вид кредита");
        List<CreditOffer> credits = creditOfferService.getAllCreditOffers();
        creditGrid.setItems(credits);
        creditGrid.asSingleSelect().addSingleSelectionListener(event -> addUI.editConfigure(event.getValue()));
    }
}
