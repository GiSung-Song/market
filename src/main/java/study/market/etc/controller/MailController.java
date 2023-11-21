package study.market.etc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.market.etc.service.MailService;
import study.market.etc.service.RedisService;
import study.market.member.dto.MemberFindPwReqDto;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;
    private final RedisService redisService;

    @ResponseBody
    @PostMapping("/sendAuth")
    public String sendAuthKey(@RequestBody MemberSignUpReqDto dto) {

        log.info("email : {}", dto.getEmail());
        mailService.sendAuth(dto.getEmail());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/checkAuth")
    public boolean matchKeyAndSignUp(@RequestBody MemberSignUpReqDto dto) {

        return redisService.matchKey(dto.getEmail(), dto.getAuthCode());
    }

    @ResponseBody
    @PostMapping("/sendTempPass")
    public void sendTempPass(@RequestBody MemberFindPwReqDto dto) {

        log.info("email : {}", dto.getEmail());
        String tmpPass = mailService.sendTemPass(dto.getEmail());

        memberService.editTmpPassword(dto.getEmail(), tmpPass);
    }

}
