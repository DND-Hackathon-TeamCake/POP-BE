package com.cake.pop.domain.letter.controller;

import com.cake.pop.domain.letter.dto.request.CreateLetterRequest;
import com.cake.pop.domain.letter.dto.request.CreateStorageRequest;
import com.cake.pop.domain.letter.dto.response.GetLetterResponse;
import com.cake.pop.domain.letter.dto.response.GetLettersResponse;
import com.cake.pop.domain.letter.dto.response.SimpleLetterDto;
import com.cake.pop.domain.letter.service.LetterService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        letterService.createLetter(request);
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

    @PostMapping("/storage")
    public ResponseEntity<Void> createStorage(@AuthenticationPrincipal Long userId, @RequestBody @Valid CreateStorageRequest request) {
        letterService.createStorage(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/storages")
    public ResponseEntity<List<SimpleLetterDto>> getStorages(@AuthenticationPrincipal Long userId) {
        List<SimpleLetterDto> response = letterService.getStorages(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{letterId}/report")
    public ResponseEntity<Void> reportLetter(@PathVariable("letterId") Long letterId) {
        letterService.reportLetter(letterId);
        return ResponseEntity.ok().build();
    }
}
