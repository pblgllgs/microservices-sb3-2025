package com.pblgllgs.accounts.service.impl;
/*
 *
 * @author pblgl
 * Created on 25-03-2025
 *
 */

import com.pblgllgs.accounts.dto.*;
import com.pblgllgs.accounts.entity.Accounts;
import com.pblgllgs.accounts.entity.Customer;
import com.pblgllgs.accounts.exception.ResourceNotFoundException;
import com.pblgllgs.accounts.mapper.AccountsMapper;
import com.pblgllgs.accounts.mapper.CustomerMapper;
import com.pblgllgs.accounts.repository.AccountsRepository;
import com.pblgllgs.accounts.repository.CustomerRepository;
import com.pblgllgs.accounts.service.ICustomerService;
import com.pblgllgs.accounts.service.client.CardsFeignClient;
import com.pblgllgs.accounts.service.client.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    public CustomerServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository, CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        mobileNumber
                ));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account",
                        "customerId",
                        customer.getCustomerId().toString()
                ));
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
