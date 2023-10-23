package study.market.member.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        return "member/signupMemberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("member") MemberSignUpReqDto dto,
                         BindingResult bindingResult,
                         HttpSession session,
                         Model model) {

        if (bindingResult.hasErrors()) {

            Boolean isDuplicate = (Boolean) session.getAttribute("isDuplicate");
            String email = (String) session.getAttribute("email");

            if (isDuplicate != null && email != null) {

                if (dto.getEmail().equals(email) && isDuplicate == false) {
                    model.addAttribute("check", "Y");
                } else {
                    model.addAttribute("check", "N");
                }

            }

            //세션 데이터 삭제
            session.removeAttribute("isDuplicate");
            session.removeAttribute("email");

            return "member/signupMemberForm";
        }

        memberService.signUp(dto);

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/checkEmail")
    public boolean isDuplicatedEmail(@RequestBody String email, HttpSession session) {

        log.info("email : {}", email);

        boolean isDuplicate = memberService.isDuplicatedEmail(email);

        session.setAttribute("email", email);
        session.setAttribute("isDuplicate", isDuplicate);

        return isDuplicate;
    }

}
