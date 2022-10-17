package com.internationalmessenger.api.service.impl;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.*;
import com.internationalmessenger.api.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslateServiceImpl implements TranslateService {
    @Autowired
    private AmazonTranslate translate;

    @Override
    public List<Language> getAllLanguages() {
        ListLanguagesRequest request = new ListLanguagesRequest();
        ListLanguagesResult result = translate.listLanguages(request);
        return result.getLanguages().stream().filter(l -> !l.getLanguageCode().equals("auto")).collect(Collectors.toList());
    }

    @Override
    public String translate(String text, String locale) {
        TranslateTextRequest request = new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode("auto")
                .withTargetLanguageCode(locale);
        TranslateTextResult result  = translate.translateText(request);
        return result.getTranslatedText();
    }
}
