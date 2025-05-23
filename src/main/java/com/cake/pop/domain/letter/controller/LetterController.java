package com.cake.pop.domain.letter.controller;

import com.cake.pop.domain.letter.dto.request.CreateLetterRequest;
import com.cake.pop.domain.letter.service.LetterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    @PostMapping
    public ResponseEntity<Void> createLetter(@RequestBody @Valid CreateLetterRequest request){
        letterService.create(request);

        return ResponseEntity.ok().build();
    }
}
