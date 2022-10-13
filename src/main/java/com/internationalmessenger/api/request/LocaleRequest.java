package com.internationalmessenger.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocaleRequest {
    @NotBlank
    private Long chatId;
    @NotBlank
    private Long userId;
    @NotBlank
    private String language;
}
