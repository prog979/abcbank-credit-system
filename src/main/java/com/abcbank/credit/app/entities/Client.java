package com.abcbank.credit.app.entities;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private String pasportNumber;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    public Client() {

    }

    public Client(String secname, String name, String patronymic, String phoneNumber, String email,
                  String pasportNumber) {
        this.secname = secname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pasportNumber = pasportNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecname() {
        return secname;
    }

    public void setSecname(String secname) {
        this.secname = secname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasportNumber() {
        return pasportNumber;
    }

    public void setPasportNumber(String pasportNumber) {
        this.pasportNumber = pasportNumber;
    }

    public Set<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        creditOffers.add(creditOffer);
    }

    public String getInitials() {
        String initials = secname + " " + name.charAt(0) + ". " + patronymic.charAt(0) + ".";
        return initials;
    }
}
