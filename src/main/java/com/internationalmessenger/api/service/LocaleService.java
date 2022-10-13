package com.internationalmessenger.api.service;

import com.internationalmessenger.api.entity.Locale;
import com.internationalmessenger.api.request.LocaleRequest;

public interface LocaleService {
    Locale getByChatIdAndAuthUser(Long id);
    Locale save(LocaleRequest request);
}
