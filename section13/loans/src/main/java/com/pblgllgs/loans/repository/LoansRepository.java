package com.pblgllgs.loans.repository;
/*
 *
 * @author pblgl
 * Created on 26-02-2025
 *
 */

import com.pblgllgs.loans.entity.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoansRepository extends CrudRepository<Loans,Long> {

    Optional<Loans> findByMobileNumber(String mobileNumber);

    Optional<Loans> findByLoanNumber(String loanNumber);
}
