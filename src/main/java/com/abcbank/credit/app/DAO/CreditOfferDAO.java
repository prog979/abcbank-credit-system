package com.abcbank.credit.app.DAO;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.entities.CreditOffer;

import java.sql.SQLException;
import java.util.List;

public interface CreditOfferDAO {
    public void addCreditOffer(CreditOffer creditOffer) throws SQLException;
    public void updateCreditOffer(CreditOffer creditOffer) throws SQLException;
    public List getAllCreditOffers() throws SQLException;
    public CreditOffer getCreditOfferById(String id) throws SQLException;
    public void deleteCreditOffer(CreditOffer creditOffer) throws SQLException;
    public List getCreditOffersByClient(Client client) throws SQLException;
    public List getCreditOffersByCredit(Credit credit) throws SQLException;
}