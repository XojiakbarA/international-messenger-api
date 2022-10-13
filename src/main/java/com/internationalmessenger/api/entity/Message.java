package com.internationalmessenger.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Message extends Base {
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;
}
