package com.internationalmessenger.api.repository;

import com.internationalmessenger.api.entity.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocaleRepository extends JpaRepository<Locale, Long> {
    Locale findByChatIdAndUserEmail(Long id, String email);
}
