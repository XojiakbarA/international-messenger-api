package com.internationalmessenger.api.controller;

import com.amazonaws.services.translate.model.Language;
import com.internationalmessenger.api.response.ApiResponse;
import com.internationalmessenger.api.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    @Autowired
    private TranslateService translateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllLanguages() {
        List<Language> languages = translateService.getAllLanguages();
        ApiResponse response = new ApiResponse();
        response.setMessage("OK");
        response.setContent(languages);
        return response;
    }
}
