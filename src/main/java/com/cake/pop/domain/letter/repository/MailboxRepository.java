package com.cake.pop.domain.letter.repository;

import com.cake.pop.domain.letter.exception.LetterErrorCode;
import com.cake.pop.entity.Mailbox;
import com.cake.pop.entity.enums.Region;
import com.cake.pop.global.exception.RestApiException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
    Optional<Mailbox> findFirstByRegion(Region region);

    List<Mailbox> findTop3ByOrderByLetterCountDesc();

    default Mailbox getFirstByRegion(Region region) {
        return findFirstByRegion(region)
                .orElseThrow(() -> new RestApiException(LetterErrorCode.MAILBOX_NOT_FOUND));
    }
}
