package com.cake.pop.entity;

import com.cake.pop.entity.enums.Region;
import com.cake.pop.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mailbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mailbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mailbox_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private Region region;

    @Column(name = "letter_count")
    private Integer letterCount;

    @Builder(access = AccessLevel.PRIVATE)
    private Mailbox(Region region) {
        this.region = region;
        this.letterCount = 0;
    }

    public static Mailbox of(Region region) {
        return Mailbox.builder()
                .region(region)
                .build();
    }
}
