package com.abcbank.credit.app.service;

import com.abcbank.credit.app.dao.ClientDAOImpl;
import com.abcbank.credit.app.dao.CreditOfferDAO;
import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.entities.CreditOffer;
import com.abcbank.credit.app.hibernate.Factory;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreditOfferService {
    private static Logger LOG = LoggerFactory.getLogger(CreditOfferService.class);
    private CreditOfferDAO factory = Factory.getInstance().getCreditOfferDAO();

    public void addCreditOffer(CreditOffer creditOffer) throws SQLException {
        factory.addCreditOffer(creditOffer);
        LOG.debug("Credit sum" + creditOffer.getCreditSum());
        LOG.debug("Credit_id" + creditOffer.getCredit().getId());
        LOG.debug("Client_id" + creditOffer.getClient().getId());

    }

    public void updateCreditOffer(CreditOffer creditOffer) throws SQLException {
        factory.updateCreditOffer(creditOffer);
    }

    public void deleteCreditOffer(CreditOffer creditOffer) throws SQLException {
        factory.deleteCreditOffer(creditOffer);
    }

    public List getAllCreditOffers() throws SQLException {
        return factory.getAllCreditOffers();
    }

    public CreditOffer getCreditOfferById(String id) throws SQLException {
        return factory.getCreditOfferById(id);
    }

    public List getCreditOfferByClient(Client client) throws SQLException {
        return factory.getCreditOffersByClient(client);
    }


    public List getCreditOfferByCredit(Credit credit) throws SQLException {
        if (credit == null) {
            return getAllCreditOffers();
        } else return factory.getCreditOffersByCredit(credit);
    }

}
