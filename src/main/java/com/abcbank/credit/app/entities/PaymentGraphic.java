package com.abcbank.credit.app.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
@Entity
@Table(name = "payment_schedules")
public class PaymentGraphic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
	private Long id;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditOffer_id", nullable = false)
    private CreditOffer creditOffer;
    
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
    private Long payment_sum;
    private Long credit_body;
    private Long credit_percents;
    private Long payment_rest;

    public PaymentGraphic(){
    }

    public PaymentGraphic(CreditOffer creditOffer, LocalDate date, Long payment_sum,
    		Long credit_body, Long credit_percents, Long payment_rest) {
        ZoneId zoneId = ZoneId.systemDefault();
        this.date = Date.from(date.atStartOfDay(zoneId).toInstant());
        this.payment_sum = payment_sum;
        this.credit_body = credit_body;
        this.credit_percents = credit_percents;
        this.payment_rest = payment_rest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    public LocalDate getDate() {
        LocalDate date = this.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    public Date getOrigDate(){
        return date;
    }

    public void setDate(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        this.date = Date.from(date.atStartOfDay(zoneId).toInstant());
    }


    public Long getPaymentSum() {
        return payment_sum;
    }

    public void setPaymentSum(Long payment_sum) {
        this.payment_sum = payment_sum;
    }

    public Long getCreditBody() {
        return credit_body;
    }

    public void setCreditBody(Long credit_body) {
        this.credit_body = credit_body;
    }

    public Long getCreditPercents() {
        return credit_percents;
    }

    public void setCreditPercents(Long credit_percents) {
        this.credit_percents = credit_percents;
    }

    public Long getPaymentRest() {
        return payment_rest;
    }

    public void setPaymentRest(Long payment_rest) {
        this.payment_rest = payment_rest;
    }
}
