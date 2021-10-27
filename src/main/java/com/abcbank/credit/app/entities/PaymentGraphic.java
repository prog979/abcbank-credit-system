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
	private Long id;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditOffer_id", nullable = false)
    private CreditOffer creditOffer;
    
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
    private Long paymentSum;
    private Long creditBody;
    private Long creditPercents;
    private Long paymentRest;

    public PaymentGraphic(){
    }

    public PaymentGraphic(CreditOffer creditOffer, LocalDate date, Long paymentSum,
    		Long creditBody, Long creditPercents, Long paymentRest) {
        ZoneId zoneId = ZoneId.systemDefault();
        this.date = Date.from(date.atStartOfDay(zoneId).toInstant());
        this.paymentSum = paymentSum;
        this.creditBody = creditBody;
        this.creditPercents = creditPercents;
        this.paymentRest = paymentRest;
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
        return paymentSum;
    }

    public void setPaymentSum(Long paymentSum) {
        this.paymentSum = paymentSum;
    }

    public Long getCreditBody() {
        return creditBody;
    }

    public void setCreditBody(Long creditBody) {
        this.creditBody = creditBody;
    }

    public Long getCreditPercents() {
        return creditPercents;
    }

    public void setCreditPercents(Long creditPercents) {
        this.creditPercents = creditPercents;
    }

    public Long getPaymentRest() {
        return paymentRest;
    }

    public void setPaymentRest(Long paymentRest) {
        this.paymentRest = paymentRest;
    }
}
