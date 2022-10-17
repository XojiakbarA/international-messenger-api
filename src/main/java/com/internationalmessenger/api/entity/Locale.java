package com.internationalmessenger.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "locales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locale extends Base {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "language_name")
    private String languageName;

    @Column(name = "language_code")
    private String languageCode;
}
