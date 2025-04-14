package com.pblgllgs.accounts.controller;
/*
 *
 * @author pblgl
 * Created on 25-03-2025
 *
 */

import com.pblgllgs.accounts.dto.CustomerDetailsDto;
import com.pblgllgs.accounts.dto.ErrorResponseDto;
import com.pblgllgs.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Customer",
        description = "CRUD REST APIs to FETCH customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Fetch customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestHeader("correlation-id") String correlationId,
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
            String mobileNumber) {
        LOGGER.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        LOGGER.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
