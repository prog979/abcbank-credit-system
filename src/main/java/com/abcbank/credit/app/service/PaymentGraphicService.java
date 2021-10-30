package com.abcbank.credit.app.service;

import com.abcbank.credit.app.dao.PaymentGraphicDAO;

import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.entities.PaymentGraphic;
import com.abcbank.credit.app.hibernate.Factory;

import java.sql.SQLException;
import java.util.List;

public class PaymentGraphicService {
    PaymentGraphicDAO factory = Factory.getInstance().getPaymentGraphicDAO();

    public void addPaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        factory.addPaymentGraphic(paymentGraphic);
    }
    public void updatePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException{
        factory.updatePaymentGraphic(paymentGraphic);
    }
    public void deletePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException{
        factory.deletePaymentGraphic(paymentGraphic);
    }
    public List getAllPaymentGraphics() throws SQLException{
        return factory.getAllPaymentGraphic();
    }
    public PaymentGraphic getPaymentGraphicById(String id) throws SQLException{
        return factory.getPaymentGraphicById(id);
    }
    public List getPaymentGraphicByCreditOffer(CreditOffer creditOffer) throws SQLException{
        if (creditOffer == null){
            return getAllPaymentGraphics();
        } else {
            return factory.getPaymentGraphicsByCreditOffer(creditOffer);
        }
    }
}
