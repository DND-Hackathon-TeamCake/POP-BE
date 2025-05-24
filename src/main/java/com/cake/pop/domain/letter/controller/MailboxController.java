package com.cake.pop.domain.letter.controller;

import com.cake.pop.domain.letter.dto.response.GetRankResponse;
import com.cake.pop.domain.letter.service.MailboxService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mailboxes")
@RequiredArgsConstructor
public class MailboxController {

    private final MailboxService mailboxService;

    @GetMapping("/rank")
    public ResponseEntity<List<GetRankResponse>> getRanks(){
        List<GetRankResponse> response = mailboxService.getRanks();
        return ResponseEntity.ok(response);
    }
}
