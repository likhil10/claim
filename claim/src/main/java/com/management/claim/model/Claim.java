package com.management.claim.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "claim_management")
public class Claim {
	/** primary key	*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claim_type",nullable = false)
    private String claimType;
    
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "purpose", nullable = false)
    private String purpose = "";

    @Column(name = "comment")
    private String comment;
    
    public Claim() {
	}
    
	public Claim(String claimType, Date fromDate, Date toDate, Double amount, String comment, String purpose) {
		this.claimType = claimType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.amount = amount;
		this.purpose = purpose;
		this.comment = comment;
	}
    
    public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Long getId() {
		return id;
	}
}
