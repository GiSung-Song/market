package study.market.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.market.member.dto.MemberFindPwReqDto;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.service.MemberService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
        return "redirect:/member/login";
    }

    @ResponseBody
    @PostMapping("/checkEmail")
    public boolean isDuplicatedEmail(@RequestBody MemberSignUpReqDto dto) {

        log.info("email : {}", dto.getEmail());

        return memberService.isDuplicatedEmail(dto.getEmail());
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "/member/loginForm";
    }

    @GetMapping("/findPw")
    public String findIdForm(@RequestParam(value = "exist", required = false) String exist,
                             @RequestParam(value = "message", required = false) String message,
                             Model model, @ModelAttribute("member") MemberFindPwReqDto dto) {

        model.addAttribute("exist", exist);
        model.addAttribute("message", message);

        return "/member/findPwForm";
    }

    @PostMapping("/findPw")
    @ResponseBody
    public Boolean findId(@RequestBody MemberFindPwReqDto dto) {

        log.info("email : {}, name : {}", dto.getEmail(), dto.getName());
        return memberService.isExistMember(dto);
    }

}
