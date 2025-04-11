package com.pblgllgs.accounts.service.client;
/*
 *
 * @author pblgl
 * Created on 03-04-2025
 *
 */

import com.pblgllgs.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
