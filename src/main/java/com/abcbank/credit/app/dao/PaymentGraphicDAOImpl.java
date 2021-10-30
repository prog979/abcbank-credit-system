package com.abcbank.credit.app.dao;

import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.entities.PaymentGraphic;
import com.abcbank.credit.app.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentGraphicDAOImpl implements PaymentGraphicDAO {
    private static Logger log = LoggerFactory.getLogger(PaymentGraphicDAOImpl.class);

    @Override
    public void addPaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(paymentGraphic);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updatePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(paymentGraphic);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List getAllPaymentGraphic() throws SQLException {
        Session session = null;
        List<PaymentGraphic> paymentGraphics = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            paymentGraphics = session.createQuery("SELECT p FROM PaymentGraphic p").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return paymentGraphics;
    }


    @Override
    public PaymentGraphic getPaymentGraphicById(String id) throws SQLException {
        Session session = null;
        PaymentGraphic paymentGraphic = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            paymentGraphic = (PaymentGraphic) session.load(PaymentGraphic.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return paymentGraphic;
    }

    @Override
    public void deletePaymentGraphic(PaymentGraphic paymentGraphic) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(paymentGraphic);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List getPaymentGraphicsByCreditOffer(CreditOffer creditOffer) throws SQLException {
        Session session = null;
        List paymentGraphics = new ArrayList<PaymentGraphic>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Long creditOffer_id = creditOffer.getId();
            Query query = session.createQuery("SELECT graphic FROM PaymentGraphic graphic " +
                    "INNER JOIN graphic.creditOffer offer WHERE offer.id = :creditOffer_id").setParameter("creditOffer_id", creditOffer_id);
            paymentGraphics = (List<PaymentGraphic>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return paymentGraphics;
    }
}
