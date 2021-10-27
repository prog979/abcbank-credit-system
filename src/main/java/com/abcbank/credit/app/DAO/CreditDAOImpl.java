package com.abcbank.credit.app.DAO;

import com.abcbank.credit.app.entities.Client;
import com.abcbank.credit.app.entities.Credit;
import com.abcbank.credit.app.hibernate.HibernateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreditDAOImpl implements CreditDAO {
	private static Logger LOG = LoggerFactory.getLogger(CreditDAOImpl.class);
	@Override
	public void addCredit(Credit client) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(client);
			session.getTransaction().commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Ошибка 'addCredit'", JOptionPane.OK_OPTION);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public void updateCredit(Credit credit) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(credit);
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
	public List getAllCredits() throws SQLException {
		List credits = new ArrayList<Credit>();
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			credits = session.createQuery("SELECT credit FROM Credit credit").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return credits;
	}

	@Override
	public Credit getCreditById(String id) throws SQLException {
		Credit credit = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			credit = (Credit) session.load(Credit.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen())
				;
		}
		return credit;
	}

	@Override
	public void deleteCredit(Credit credit) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(credit);
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
	public List getCreditsByClient(Client client) throws SQLException {
		return null;
	}

	@Override
	public List getCreditsByName(String credit) throws SQLException {
		Session session = null;
		credit.toLowerCase(Locale.ROOT);
		List<Credit> credits = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			credits = session.createQuery(
							"SELECT credit FROM Credit credit WHERE lower(credit.creditName) like concat('%', :credit, '%')")
					.setParameter("credit", credit).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return credits;
	}
}
