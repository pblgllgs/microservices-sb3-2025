package com.pblgllgs.loans.entity;
/*
 *
 * @author pblgl
 * Created on 26-02-2025
 *
 */

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Loans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;

}