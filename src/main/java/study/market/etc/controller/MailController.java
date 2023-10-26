package study.market.etc.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.market.etc.dto.MailKeyReqDto;
import study.market.etc.service.MailService;
import study.market.etc.service.RedisService;
import study.market.member.dto.MemberSignUpReqDto;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final RedisService redisService;

    @ResponseBody
    @PostMapping("/sendAuth")
    public String sendAuthKey(@RequestBody MemberSignUpReqDto dto) {

        log.info("email : {}", dto.getEmail());
        mailService.sendMail(dto.getEmail());

        return "ok";
    }

    @PostMapping("/auth")
    public String matchKeyAndSignUp(@ModelAttribute("dto") MailKeyReqDto dto) {

        if(!redisService.getData(dto.getEmail()).equals(dto.getAuthKey())) {
            return "/mail/mailAuthForm";
        }

        return "redirect:/";

    }

}
