package com.abcbank.credit.app.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(36)", nullable = false)
    private String id;
    private String credit_name;

    @Column(nullable = false)
    private Long credit_limit;
    @Column(nullable = false)
    private Integer percent;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    public Credit() {
    }

    public Credit(String credit_name, Long credit_limit, Integer percent) {
        this.credit_name = credit_name;
        this.credit_limit = credit_limit;
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditName() {
        return credit_name;
    }

    public void setCreditName(String credit_name) {
        this.credit_name = credit_name;
    }

    public Long getCreditLimit() {
        return credit_limit;
    }

    public void setCreditLimit(Long credit_limit) {
        this.credit_limit = credit_limit;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Set<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        creditOffers.add(creditOffer);
    }
}