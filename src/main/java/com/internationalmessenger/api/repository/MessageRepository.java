package com.internationalmessenger.api.repository;

import com.internationalmessenger.api.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(Long id);
}
