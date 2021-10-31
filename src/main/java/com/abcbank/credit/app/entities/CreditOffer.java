package com.abcbank.credit.app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "credit_offers")
public class CreditOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_Id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_Id", nullable = false)
    private Credit credit;

    private Long credit_sum;
    private Long months_of_credit;
    @OneToMany(mappedBy = "creditOffer", fetch = FetchType.EAGER)
    private Set<PaymentGraphic> payment_graphics;

    public CreditOffer() {
    }

    public CreditOffer(Client client, Credit credit, Long credit_sum, Long months_of_credit) {
        this.client = client;
        this.credit = credit;
        this.credit_sum = credit_sum;
        this.months_of_credit = months_of_credit;
    }

       public Long getMonthsOfCredit() {
        return months_of_credit;
    }

    public void setMonthsOfCredit(Long months_of_credit) {
        this.months_of_credit = months_of_credit;
    }

    public Set<PaymentGraphic> getPayment_graphics() {
        return payment_graphics;
    }

    public void setPayment_graphics(Set<PaymentGraphic> payment_graphics) {
        this.payment_graphics = payment_graphics;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreditSum() {
        return credit_sum;
    }

    public void setCreditSum(Long credit_sum) {
        this.credit_sum = credit_sum;
    }

    public Set getPayment_graphic() {
        return payment_graphics;
    }

    public void setPayment_graphic(PaymentGraphic payment_graphic) {
        payment_graphics.add(payment_graphic);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}