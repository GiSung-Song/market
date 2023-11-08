package study.market.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.market.member.dto.MemberEditPasswordReqDto;
import study.market.member.dto.MemberFindPwReqDto;
import study.market.member.dto.MemberFormDto;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.service.MemberService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;

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
    public String findPwForm(@ModelAttribute("member") MemberFindPwReqDto dto) {
        return "/member/findPwForm";
    }

    @PostMapping("/findPw")
    @ResponseBody
    public Boolean findPw(@RequestBody MemberFindPwReqDto dto) {

        log.info("email : {}, name : {}", dto.getEmail(), dto.getName());
        return memberService.isExistMember(dto);
    }

    @GetMapping("/info")
    public String memberInfoForm(Principal principal, Model model) {

        String email = principal.getName(); //현재 로그인 한 계정의 이메일

        MemberFormDto member = memberService.getMemberInfo(email);
        model.addAttribute("member", member);

        return "/member/infoMemberForm";
    }

    @PostMapping("/info")
    public String editMemberInfo(@Valid @ModelAttribute("member") MemberFormDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/member/infoMemberForm";
        }

        memberService.editMember(dto);

        return "redirect:/member/info";
    }

    @GetMapping("/editPw")
    public String editPasswordForm(@ModelAttribute("member") MemberEditPasswordReqDto dto) {
        return "/member/editPasswordForm";
    }

    @PostMapping("/editPw")
    @ResponseBody
    public Boolean editPassword(@RequestBody MemberEditPasswordReqDto dto, Principal principal) {
        String email = principal.getName();

        log.info("email : {}, nowPassword : {}, password : {}", email, dto.getNowPassword(), dto.getPassword());

        return memberService.editPassword(email, dto);
    }
}
