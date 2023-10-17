package study.market.member.service;

import study.market.member.dto.MemberSignUpReqDto;

public interface MemberService {

    Long signUp(MemberSignUpReqDto dto); //회원가입
    boolean isDuplicatedEmail(String email); //이메일 중복 확인
    void editPassword(Long id, String password); //패스워드 변경

}
