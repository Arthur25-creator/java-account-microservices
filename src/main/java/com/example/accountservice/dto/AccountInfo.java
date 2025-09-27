package com.example.accountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountInfo {

    @NotBlank(message = "Customer name is required")
    @Size(max = 50)
    private String customerName;

    @NotBlank(message = "Customer mobile is required")
    @Size(max = 20)
    private String customerMobile;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Email should be valid")
    @Size(max = 50)
    private String customerEmail;

    @NotBlank(message = "Address1 is required")
    @Size(max = 100)
    private String address1;

    @Size(max = 100)
    private String address2;

    @NotBlank(message = "Account type is required")
    private String accountType;

}
