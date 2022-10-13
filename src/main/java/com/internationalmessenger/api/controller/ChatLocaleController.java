package com.internationalmessenger.api.controller;

import com.internationalmessenger.api.entity.Locale;
import com.internationalmessenger.api.response.ApiResponse;
import com.internationalmessenger.api.service.LocaleService;
import com.internationalmessenger.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
public class ChatLocaleController {
    @Autowired
    private LocaleService localeService;
    @Autowired
    private Mapper mapper;

    @GetMapping("/{id}/locales")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getLocaleByChatIdAndAuthUser(@PathVariable Long id) {
        Locale locale = localeService.getByChatIdAndAuthUser(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("OK");
        response.setContent(mapper.convertToLocaleDTO(locale));
        return response;
    }
}
