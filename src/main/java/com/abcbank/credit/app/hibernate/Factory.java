package com.abcbank.credit.app.hibernate;

import com.abcbank.credit.app.DAO.ClientDAO;
import com.abcbank.credit.app.DAO.ClientDAOImpl;
import com.abcbank.credit.app.DAO.CreditDAO;
import com.abcbank.credit.app.DAO.CreditDAOImpl;
import com.abcbank.credit.app.DAO.CreditOfferDAO;
import com.abcbank.credit.app.DAO.CreditOfferDAOImpl;
import com.abcbank.credit.app.DAO.PaymentGraphicDAO;
import com.abcbank.credit.app.DAO.PaymentGraphicDAOImpl;
import com.abcbank.credit.app.hibernate.Factory;

public class Factory {
    private static ClientDAO clientDAO = null;
    private static CreditDAO creditDAO = null;
    private static CreditOfferDAO creditOfferDAO = null;
    private static PaymentGraphicDAO paymentGraphicDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public ClientDAO getClientDAO(){
        if (clientDAO == null){
            clientDAO = new ClientDAOImpl();
        }
        return clientDAO;
    }

    public CreditDAO getCreditDAO(){
        if (creditDAO == null){
            creditDAO = new CreditDAOImpl();
        }
        return creditDAO;
    }

    public CreditOfferDAO getCreditOfferDAO(){
        if (creditOfferDAO == null){
            creditOfferDAO = new CreditOfferDAOImpl();
        }
        return creditOfferDAO;
    }

    public PaymentGraphicDAO getPaymentGraphicDAO(){
        if (paymentGraphicDAO == null){
            paymentGraphicDAO = new PaymentGraphicDAOImpl();
        }
        return paymentGraphicDAO;
    }
}
