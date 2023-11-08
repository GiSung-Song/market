package study.market.member.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.market.member.dto.MemberEditPasswordReqDto;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void save() {

        MemberSignUpReqDto dto = getDto();

        Long savedMemberId = memberService.signUp(dto);

        Member findMember = memberRepository.findById(savedMemberId).get();

        assertThat(savedMemberId).isEqualTo(findMember.getId());
        assertThat(dto.getPhoneNumber()).isEqualTo(findMember.getPhoneNumber());

    }

    @Test
    void isDuplicatedEmail() {

        MemberSignUpReqDto dto = getDto();
        Long savedMember = memberService.signUp(dto);

        String email1 = "test@gmail.com";
        String email2 = "test2@gmail.com";
        boolean duplication1 = memberService.isDuplicatedEmail(email1);
        boolean duplication2 = memberService.isDuplicatedEmail(email2);

        assertThat(duplication1).isTrue();
        assertThat(duplication2).isFalse();

    }

    @Test
    void editTmpPassword() {
        MemberSignUpReqDto dto = getDto();
        Long savedMemberId = memberService.signUp(dto);

        String newPassword = "test1235";
        memberService.editTmpPassword(dto.getEmail(), newPassword);

       Member member = memberRepository.findById(savedMemberId).get();
       assertThat(member.getPassword()).isEqualTo(newPassword);
        assertThat(member.getId()).isEqualTo(savedMemberId);

    }

    @Test
    void editPassword() {
        MemberSignUpReqDto dto = getDto();
        Long savedMemberId = memberService.signUp(dto);

        MemberEditPasswordReqDto newDto = new MemberEditPasswordReqDto();
        newDto.setNowPassword("test1234");
        newDto.setPassword("test1235");

        memberService.editPassword(dto.getEmail(), newDto);

        Member member = memberRepository.findById(savedMemberId).get();
        boolean matches = passwordEncoder.matches("test1235", member.getPassword());

        assertThat(matches).isTrue();
        assertThat(member.getId()).isEqualTo(savedMemberId);

    }

    MemberSignUpReqDto getDto() {
        MemberSignUpReqDto dto = new MemberSignUpReqDto();
        dto.setEmail("test@gmail.com");
        dto.setPassword("test1234");
        dto.setName("테스터");
        dto.setPhoneNumber("010-1234-5678");
        dto.setAddress("서울시 도봉구 방학로 200");
        dto.setDetailAddress("115동 413호");
        dto.setZipCode("13579");

        return dto;
    }

}