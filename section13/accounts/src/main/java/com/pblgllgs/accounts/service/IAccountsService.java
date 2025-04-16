package com.pblgllgs.accounts.service;

import com.pblgllgs.accounts.dto.CustomerDto;

import java.util.List;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String mobileNumber);
    List<CustomerDto> fetchAllCustomers();
    boolean updateCommunicationStatus(Long accountNumber);
}
