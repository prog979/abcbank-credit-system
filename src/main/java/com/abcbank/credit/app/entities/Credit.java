package com.abcbank.credit.app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creditName;
    private Long creditLimit;
    private Integer percent;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    public Credit() {

    }

    public Credit(String creditName, Long creditLimit, Integer percent) {
        this.creditName = creditName;
        this.creditLimit = creditLimit;
        this.percent = percent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
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