package study.market.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.market.member.MemberStatus;
import study.market.member.Role;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;

/**
 * signUp : 회원가입(회원가입 폼 dto)
 * isDuplicatedEmail : 이메일 중복 검사(String : 이메일)
 * editPassword : 패스워드 변경(Long : id, String : 패스워드)
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long signUp(MemberSignUpReqDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .zipCode(dto.getZipCode())
                .memberStatus(MemberStatus.ACTIVE)
                .role(Role.USER)
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isDuplicatedEmail(String email) {
        Member findMember = memberRepository.findByEmail(email);

        return findMember != null ? true : false;
    }

    @Transactional
    @Override
    public void editPassword(Long id, String password) {
        Member member = memberRepository.findById(id).get();

        member.editPassword(password);
        memberRepository.save(member);
    }


}
