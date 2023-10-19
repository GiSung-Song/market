package study.market.etc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.market.etc.dto.MailKeyReqDto;
import study.market.etc.service.MailService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send-mail/key")
    public String sendKey(@RequestBody MailKeyReqDto dto) {

        mailService.sendMail(dto.getEmail());

        return "ok";

    }

}
