package com.abcbank.credit.app.dao;

import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.entities.PaymentGraphic;

import java.sql.SQLException;
import java.util.List;

public interface PaymentGraphicDAO {
    public void addPaymentGraphic (PaymentGraphic paymentGraphic) throws SQLException;
    public void updatePaymentGraphic (PaymentGraphic paymentGraphic) throws SQLException;
    public List getAllPaymentGraphic () throws SQLException;
    public PaymentGraphic getPaymentGraphicById(String id) throws SQLException;
    public void deletePaymentGraphic (PaymentGraphic paymentGraphic) throws SQLException;
    public List getPaymentGraphicsByCreditOffer(CreditOffer creditOffer) throws SQLException;
}