package com.cake.pop.domain.letter.controller;

import com.cake.pop.domain.letter.dto.request.CreateLetterRequest;
import com.cake.pop.domain.letter.dto.response.GetLetterResponse;
import com.cake.pop.domain.letter.dto.response.GetLettersResponse;
import com.cake.pop.domain.letter.service.LetterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    @PostMapping
    public ResponseEntity<Void> createLetter(@RequestBody @Valid CreateLetterRequest request) {
        letterService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<GetLettersResponse> getLetters(@RequestParam("region") String region) {
        GetLettersResponse response = letterService.getLetters(region);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{letterId}")
    public ResponseEntity<GetLetterResponse> getLetter(@PathVariable("letterId") Long letterId) {
        GetLetterResponse response = letterService.getLetter(letterId);
        return ResponseEntity.ok(response);
    }
}
