package com.cake.pop.domain.letter.service;

import com.cake.pop.domain.letter.dto.response.GetRankResponse;
import com.cake.pop.domain.letter.repository.MailboxRepository;
import com.cake.pop.entity.Mailbox;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MailboxService {

    private final MailboxRepository mailboxRepository;

    public List<GetRankResponse> getRanks() {
        List<Mailbox> topMailboxes = mailboxRepository.findTop3ByOrderByLetterCountDesc();

        return topMailboxes.stream()
                .map(mailbox -> new GetRankResponse(
                        mailbox.getRegion().getName(),
                        mailbox.getLetterCount()
                ))
                .toList();
    }
}
