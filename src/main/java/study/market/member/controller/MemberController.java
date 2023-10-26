package study.market.member.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.market.etc.service.MailService;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("member") MemberSignUpReqDto dto) {
        return "member/signupMemberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("member") MemberSignUpReqDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/signupMemberForm";
        }

        memberService.signUp(dto);
        return "redirect:/mail/auth";
    }

    @ResponseBody
    @PostMapping("/checkEmail")
    public boolean isDuplicatedEmail(@RequestBody MemberSignUpReqDto dto) {

        log.info("email : {}", dto.getEmail());

        return memberService.isDuplicatedEmail(dto.getEmail());
    }

}
