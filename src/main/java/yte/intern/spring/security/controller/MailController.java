package yte.intern.spring.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.spring.security.dto.MessageResponse;
import yte.intern.spring.security.dto.ParticipantDetailsDTO;
import yte.intern.spring.security.service.MailService;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/send-qr-code")
    public void sendEmail(@RequestBody ParticipantDetailsDTO participantDetailsDTO)  {
        mailService.emailWithImage(participantDetailsDTO, "mail");
    }

    @PostMapping("/mail/get-qr-code")
    public void getQrCode(@RequestBody ParticipantDetailsDTO participantDetailsDTO)  {
        mailService.emailWithImage(participantDetailsDTO, "qr");
    }
}
