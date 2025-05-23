package com.cake.pop.domain.letter.service;

import com.cake.pop.domain.letter.dto.request.CreateLetterRequest;
import com.cake.pop.domain.letter.dto.response.GetLettersResponse;
import com.cake.pop.domain.letter.dto.response.SimpleLetterDto;
import com.cake.pop.domain.letter.repository.LetterRepository;
import com.cake.pop.domain.letter.repository.MailboxRepository;
import com.cake.pop.entity.Letter;
import com.cake.pop.entity.Mailbox;
import com.cake.pop.entity.enums.Region;
import java.util.List;
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

    public GetLettersResponse getLetters(String region){
        Mailbox findMailbox = mailboxRepository.getFirstByRegion(Region.fromKoreanName(region));

        List<Letter> letters = letterRepository.findByMailbox(findMailbox);

        List<SimpleLetterDto> letterDtos = letters.stream()
                .map(letter -> new SimpleLetterDto(
                        letter.getId(),
                        letter.getContent(),
                        letter.getCreatedAt().toLocalDate()
                ))
                .toList();

        return new GetLettersResponse(region, letterDtos);
    }
}
