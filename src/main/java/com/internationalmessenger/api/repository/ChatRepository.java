package com.internationalmessenger.api.repository;

import com.internationalmessenger.api.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUsersEmail(String email);
}
