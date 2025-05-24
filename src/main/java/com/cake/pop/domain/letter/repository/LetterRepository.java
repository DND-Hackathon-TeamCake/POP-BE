package com.cake.pop.domain.letter.repository;

import com.cake.pop.domain.letter.exception.LetterErrorCode;
import com.cake.pop.entity.Letter;
import com.cake.pop.entity.Mailbox;
import com.cake.pop.global.exception.RestApiException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findByMailbox(Mailbox mailbox);

    List<Letter> findByMailboxAndStatus(Mailbox mailbox, com.cake.pop.entity.enums.Status status);

    @Query("SELECT l FROM Letter l WHERE l.id = :id AND l.status = 'ACTIVE'")
    Optional<Letter> findActiveById(@Param("id") Long id);

    @Query("SELECT l FROM Letter l JOIN FETCH l.mailbox WHERE l.id = :id AND l.status = 'ACTIVE'")
    Optional<Letter> findActiveByIdWithMailbox(@Param("id") Long id);

    default Letter getById(Long id) {
        return findActiveById(id)
                .orElseThrow(() -> new RestApiException(LetterErrorCode.LETTER_NOT_FOUND));
    }

    default Letter getByIdWithMailbox(Long id) {
        return findActiveByIdWithMailbox(id)
                .orElseThrow(() -> new RestApiException(LetterErrorCode.LETTER_NOT_FOUND));
    }
}
