package com.internationalmessenger.api.entity;

import com.internationalmessenger.api.entity.enums.EAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "isNonLocked", nullable = false)
    private Boolean isNonLocked = true;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private EAuthProvider provider = EAuthProvider.local;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(mappedBy = "user")
    private List<Locale> locales;
}
