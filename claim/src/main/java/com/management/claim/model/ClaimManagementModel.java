package com.management.claim.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "claimManagement")
public class ClaimManagementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claimType",nullable = false)
    private String claimType;

    @Column(name = "fromDate", nullable = false)
    private Date fromDate;

    @Column(name = "toDate", nullable = false)
    private Date toDate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "comment")
    private String comment;

    @Column(name = "purpose", nullable = false)
    private String purpose;


}
