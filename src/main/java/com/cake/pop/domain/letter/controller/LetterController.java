package com.cake.pop.domain.letter.controller;

import com.cake.pop.domain.letter.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

}
