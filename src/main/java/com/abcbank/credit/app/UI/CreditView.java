package com.abcbank.credit.app.UI;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.service.CreditService;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class CreditView extends VerticalLayout {
    private Grid<Credit> creditGrid = new Grid<>(Credit.class);
    private TextField filter = new TextField();
    private Button add = new Button("Новый");
    private CreditService creditService = new CreditService();
    private CreditEditUI addUI = new CreditEditUI(new Credit(), this);


    public CreditView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(creditGrid, addUI);
        gridLayout.setExpandRatio(creditGrid, 1);
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
        List<Credit> credits = creditService.getCreditByName(filter.getValue());
        creditGrid.setItems(credits);
    }

    public void updateGrid() throws SQLException {
        creditGrid.setItems(creditService.getAllCredits());
    }


    private void gridConfigure() throws SQLException {
        creditGrid.setWidth("900");
        creditService.fillDB();
        creditGrid.removeColumn("id");
        creditGrid.removeColumn("creditOffers");
//        creditGrid.setColumns("creditName", "creditLimit", "percent");
        creditGrid.setColumns();
        creditGrid.addColumn(Credit::getCreditName).setCaption("Вид кредита");
        creditGrid.addColumn(Credit::getCreditLimit).setCaption("Лимит по кредиту");
        creditGrid.addColumn(Credit::getPercent).setCaption("Проценты");


        List<Credit> credits = creditService.getAllCredits();
        creditGrid.setItems(credits);
        creditGrid.asSingleSelect().addSingleSelectionListener(event -> addUI.editConfigure(event.getValue()));
    }
}
