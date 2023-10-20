package study.market.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("member") MemberSignUpReqDto dto) {

        log.info("회원가입 form 이동");

        return "member/signupMemberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("member") MemberSignUpReqDto dto, BindingResult bindingResult) {

        log.info("post");


        if (bindingResult.hasErrors()) {

            log.info("has error");

            return "member/signupMemberForm";
        }

        log.info("no error");

        memberService.signUp(dto);

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/checkEmail")
    public boolean isDuplicatedEmail(@RequestBody String email) {

        log.info("email : ", email);

        return memberService.isDuplicatedEmail(email);
    }

}
