package com.cake.pop.domain.letter.service;

import com.cake.pop.domain.letter.dto.request.CreateLetterRequest;
import com.cake.pop.domain.letter.dto.request.CreateStorageRequest;
import com.cake.pop.domain.letter.dto.response.GetLetterResponse;
import com.cake.pop.domain.letter.dto.response.GetLettersResponse;
import com.cake.pop.domain.letter.dto.response.SimpleLetterDto;
import com.cake.pop.domain.letter.repository.LetterRepository;
import com.cake.pop.domain.letter.repository.MailboxRepository;
import com.cake.pop.domain.letter.repository.StorageRepository;
import com.cake.pop.domain.user.repository.UserRepository;
import com.cake.pop.entity.Letter;
import com.cake.pop.entity.Mailbox;
import com.cake.pop.entity.Storage;
import com.cake.pop.entity.User;
import com.cake.pop.entity.enums.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final MailboxRepository mailboxRepository;
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;

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

    public GetLetterResponse getLetter(Long letterId){
        Letter findLetter = letterRepository.getByIdWithMailbox(letterId);

        return new GetLetterResponse(
                findLetter.getId(),
                findLetter.getContent(),
                findLetter.getImageUrl(),
                findLetter.getMailbox().getRegion().getName()
        );
    }

    public void createStorage(Long userId, CreateStorageRequest request){
        User findUser = userRepository.getById(userId);
        Letter findLetter = letterRepository.getById(request.letterId());
        Storage storage = Storage.of(findUser, findLetter);

        storageRepository.save(storage);
    }
}
