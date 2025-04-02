package com.pblgllgs.accounts.service.impl;
/*
 *
 * @author pblgl
 * Created on 19-02-2025
 *
 */

import com.pblgllgs.accounts.constants.AccountsConstants;
import com.pblgllgs.accounts.dto.AccountsDto;
import com.pblgllgs.accounts.dto.CustomerDto;
import com.pblgllgs.accounts.entity.Accounts;
import com.pblgllgs.accounts.entity.Customer;
import com.pblgllgs.accounts.exception.CustomerAlreadyExistsException;
import com.pblgllgs.accounts.exception.ResourceNotFoundException;
import com.pblgllgs.accounts.mapper.AccountsMapper;
import com.pblgllgs.accounts.mapper.CustomerMapper;
import com.pblgllgs.accounts.repository.AccountsRepository;
import com.pblgllgs.accounts.repository.CustomerRepository;
import com.pblgllgs.accounts.service.IAccountsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number" + customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
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
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public List<CustomerDto> fetchAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> CustomerMapper.mapToCustomerDto(customer, new CustomerDto()))
                .map(customerDto -> {
                    Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(
                            () -> new ResourceNotFoundException("Customer", "mobileNumber", customerDto.getMobileNumber())
                    );
                    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Account",
                                    "customerId",
                                    customer.getCustomerId().toString()
                            ));
                    CustomerDto customerDtoFinal = CustomerMapper.mapToCustomerDto(customer, customerDto);
                    customerDtoFinal.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
                    return customerDtoFinal;
                })
                .toList();
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }


}
