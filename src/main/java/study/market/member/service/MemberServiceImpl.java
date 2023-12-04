package study.market.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import study.market.etc.config.CustomUserDetails;
import study.market.member.enumType.MemberStatus;
import study.market.member.enumType.Role;
import study.market.member.dto.MemberEditPasswordReqDto;
import study.market.member.dto.MemberFindPwReqDto;
import study.market.member.dto.MemberFormDto;
import study.market.member.dto.MemberSignUpReqDto;
import study.market.member.entity.Member;
import study.market.member.repository.MemberRepository;

import java.util.NoSuchElementException;

/**
 * signUp : 회원가입(회원가입 폼 dto)
 * isDuplicatedEmail : 이메일 중복 검사(String : 이메일)
 * editPassword : 패스워드 변경(Long : id, String : 패스워드)
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long signUp(MemberSignUpReqDto dto) {

        Role role;

        if (dto.getRole().equalsIgnoreCase("DRIVER")) {
            role = Role.DRIVER;
        } else {
            role = Role.USER;
        }

        Member member = Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .zipCode(dto.getZipCode())
                .memberStatus(MemberStatus.ACTIVE)
                .role(role)
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
    public void editTmpPassword(String email, String password) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new NoSuchElementException("해당 메일로 가입된 정보가 없습니다.");
        }

        member.editPassword(passwordEncoder.encode(password));
    }

    @Transactional
    @Override
    public boolean editPassword(String email, MemberEditPasswordReqDto dto) {

        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new NoSuchElementException("해당 메일로 가입된 정보가 없습니다.");
        }

        //입력받은 현재 비밀번호가 맞는지 검사
        boolean matches = passwordEncoder.matches(dto.getNowPassword(), member.getPassword());

        if (matches == true) {
            member.editPassword(passwordEncoder.encode(dto.getPassword())); //새로운 비밀번호로 변경
            return true;
        }

        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) {

        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("해당 이메일로 가입된 정보가 없습니다.");
        }

        return new CustomUserDetails(member);

    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExistMember(MemberFindPwReqDto dto) {

        Member findMember = memberRepository.findByEmailAndName(dto.getEmail(), dto.getName());

        return findMember != null ? true : false;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberFormDto getMemberInfo(String email) {

        Member findMember = memberRepository.findByEmail(email);

        if (findMember == null) {
            throw new UsernameNotFoundException("등록된 회원이 없습니다.");
        }

        return MemberFormDto.builder()
                .email(findMember.getEmail())
                .name(findMember.getName())
                .phoneNumber(findMember.getPhoneNumber())
                .address(findMember.getAddress())
                .detailAddress(findMember.getDetailAddress())
                .zipCode(findMember.getZipCode())
                .build();
    }

    @Transactional
    @Override
    public void editMember(MemberFormDto dto) {

        Member findMember = memberRepository.findByEmail(dto.getEmail());

        if (findMember == null) {
            throw new UsernameNotFoundException("등록된 회원이 없습니다.");
        }

        //회원정보 수정
        findMember.editInfo(dto.getPhoneNumber(), dto.getAddress(), dto.getDetailAddress(), dto.getZipCode());
    }
}
