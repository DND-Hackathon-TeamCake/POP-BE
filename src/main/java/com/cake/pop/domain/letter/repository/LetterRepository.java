package com.cake.pop.domain.letter.repository;

import com.cake.pop.domain.letter.exception.LetterErrorCode;
import com.cake.pop.entity.Letter;
import com.cake.pop.entity.Mailbox;
import com.cake.pop.global.exception.RestApiException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findByMailbox(Mailbox mailbox);

    default Letter getById(Long id) {
        return findById(id).orElseThrow(() -> new RestApiException(LetterErrorCode.LETTER_NOT_FOUND));
    }
}
