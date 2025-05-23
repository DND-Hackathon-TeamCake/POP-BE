package com.cake.pop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "storage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id", nullable = false)
    Letter letter;

    @Builder(access = AccessLevel.PRIVATE)
    private Storage(User user, Letter letter) {
        this.user = user;
        this.letter = letter;
    }

    public static Storage of(User user, Letter letter) {
        return Storage.builder()
                .user(user)
                .letter(letter)
                .build();
    }
}
