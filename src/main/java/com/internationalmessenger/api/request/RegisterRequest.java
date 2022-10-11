package com.internationalmessenger.api.request;

import com.internationalmessenger.api.validator.ConfirmPassword;
import com.internationalmessenger.api.validator.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ConfirmPassword(field = "password", fieldMatch = "confirmPassword")
public class RegisterRequest {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String confirmPassword;
}
