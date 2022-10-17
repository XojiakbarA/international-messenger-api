package com.internationalmessenger.api.service;

import com.amazonaws.services.translate.model.Language;

import java.util.List;

public interface TranslateService {
    List<Language> getAllLanguages();
    String translate(String text, String locale);
}
