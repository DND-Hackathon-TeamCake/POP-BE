package com.cake.pop.domain.letter.service;

import com.cake.pop.domain.letter.dto.request.CreateLetterRequest;
import com.cake.pop.domain.letter.repository.LetterRepository;
import com.cake.pop.domain.letter.repository.MailboxRepository;
import com.cake.pop.entity.Letter;
import com.cake.pop.entity.Mailbox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final MailboxRepository mailboxRepository;

    public void create(CreateLetterRequest request) {
        Mailbox findMailbox = mailboxRepository.getFirstByRegion(request.region());
        Letter letter = Letter.of(request.content(), findMailbox, request.imageUrl());
        letterRepository.save(letter);
    }
}
