package com.abcbank.credit.app.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    private String secname;
    private String name;
    private String patronymic;
    private String phone_number;
    private String email;
    private String pasport_number;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    public Client() {

    }

    public Client(String secname, String name, String patronymic, String phone_number, String email,
                  String pasport_number) {
        this.secname = secname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone_number = phone_number;
        this.email = email;
        this.pasport_number = pasport_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasportNumber() {
        return pasport_number;
    }

    public void setPasportNumber(String pasport_number) {
        this.pasport_number = pasport_number;
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
