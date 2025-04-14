package com.pblgllgs.accounts.dto;
/*
 *
 * @author pblgl
 * Created on 25-03-2025
 *
 */

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer details",
        description = "Schema to hold Customer details, cards and loans information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer", example = "pbl gllgs"
    )
    @NotEmpty(message = "Name cant be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;
    @Schema(
            description = "Email address of the customer", example = "tutor@pblgllgs.com"
    )
    @NotEmpty(message = "Email cant be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;
    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @NotEmpty(message = "Mobile number cant be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDto;
    @Schema(
            description = "Cards details of the Customer"
    )
    private CardsDto cardsDto;
}
