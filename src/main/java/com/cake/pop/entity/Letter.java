package com.cake.pop.entity;

import com.cake.pop.entity.enums.Status;
import com.cake.pop.global.domain.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "letter")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @Column(name = "content", length = 350, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "report", nullable = false)
    private Integer report;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mailbox_id", nullable = false)
    Mailbox mailbox;

    @Builder(access = AccessLevel.PRIVATE)
    private Letter(String content, Mailbox mailbox, String imageUrl) {
        this.content = content;
        this.status = Status.ACTIVE;
        this.mailbox = mailbox;
        this.imageUrl = imageUrl;
        this.report = 0;
    }

    public static Letter of(String content, Mailbox mailbox, String imageUrl) {
        return Letter.builder()
                .content(content)
                .mailbox(mailbox)
                .imageUrl(imageUrl)
                .build();
    }
}
