package com.cake.pop.global.config;

import com.cake.pop.domain.letter.repository.MailboxRepository;
import com.cake.pop.entity.Mailbox;
import com.cake.pop.entity.enums.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailboxInitializer implements ApplicationRunner {

    private final MailboxRepository mailboxRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(mailboxRepository.count() == 0){
            for (Region region : Region.values()) {
                Mailbox mailbox = Mailbox.of(region);
                mailboxRepository.save(mailbox);
            }
        }
    }
}
