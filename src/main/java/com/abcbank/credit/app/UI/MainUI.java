package com.abcbank.credit.app.UI;

import com.vaadin.server.*;
import com.vaadin.shared.ui.*;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class MainUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeUndefined();
		layout.setMargin(false);
		setContent(layout);

		TabSheet tabSheet = new TabSheet();
		TabSheet bankTabSheet = new TabSheet();
		layout.addComponent(tabSheet);
		tabSheet.addTab(bankTabSheet, "АВС Банк");
		try {
			bankTabSheet.addTab(new CreditView(), "Виды кредитов");
			bankTabSheet.addTab(new ClientView(), "Клиенты");
			bankTabSheet.addTab(new CreditOfferView(), "Кредитные договоры");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}