package com.cake.pop.domain.letter.service;

import com.cake.pop.domain.letter.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
}
