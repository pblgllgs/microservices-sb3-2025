package com.pblgllgs.accounts.service;

import com.pblgllgs.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
