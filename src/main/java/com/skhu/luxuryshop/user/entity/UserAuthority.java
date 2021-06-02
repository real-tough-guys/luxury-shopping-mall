package com.skhu.luxuryshop.user.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @JsonBackReference
    private Authority auth;
}