package com.abcbank.credit.app.hibernate;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.entities.PaymentGraphic;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().
                    addAnnotatedClass(Client.class).
                    addAnnotatedClass(Credit.class).
                    addAnnotatedClass(CreditOffer.class).
                    addAnnotatedClass(PaymentGraphic.class).buildSessionFactory();
        } catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
