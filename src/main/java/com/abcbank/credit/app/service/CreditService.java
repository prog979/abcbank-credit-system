package com.abcbank.credit.app.service;

import com.abcbank.credit.app.dao.CreditDAO;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.hibernate.Factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditService {
    private Factory factory = Factory.getInstance();

    public void fillDB() throws SQLException {
        if (getAllCredits().isEmpty()) {
            addCredits().forEach(element -> {
                try {
                    addCredit(element);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }

    }

    private List<Credit> addCredits() throws SQLException {
        List<Credit> credits = new ArrayList<>();
        credits.add(new Credit("Аннуитетный", 50000L, 22));
        credits.add(new Credit("Срочный", 10000L, 12));
        credits.add(new Credit("Автокредит", 5500L, 12));
        credits.add(new Credit("Ипотечный", 10000000L, 7));
        credits.add(new Credit("Потребительский", 200000L, 12));

        return credits;
    }

    public void addCredit(Credit credit) throws SQLException {
        factory.getCreditDAO().addCredit(credit);
    }

    public void updateCredit(Credit credit) throws SQLException {
        factory.getCreditDAO().updateCredit(credit);
    }

    public void deleteCredit(Credit credit) throws SQLException {
        factory.getCreditDAO().deleteCredit(credit);
    }

    public List getAllCredits() throws SQLException {
        return factory.getCreditDAO().getAllCredits();
    }

    public Credit getCreditById(String id) throws SQLException {
        return factory.getCreditDAO().getCreditById(id);
    }

    public List getCreditByName(String name) throws SQLException {
        if (name == null) {
            return getAllCredits();
        } else return factory.getCreditDAO().getCreditsByName(name);
    }
}
