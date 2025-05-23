package com.cake.pop.domain.letter.repository;

import com.cake.pop.domain.letter.exception.LetterErrorCode;
import com.cake.pop.entity.Letter;
import com.cake.pop.global.exception.RestApiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    default Letter getById(Long id) {
        return findById(id).orElseThrow(() -> new RestApiException(LetterErrorCode.LETTER_NOT_FOUND));
    }
}
