package study.market.member.service;

import study.market.member.dto.MemberEditPasswordReqDto;
import study.market.member.dto.MemberFindPwReqDto;
import study.market.member.dto.MemberFormDto;
import study.market.member.dto.MemberSignUpReqDto;

public interface MemberService {

    Long signUp(MemberSignUpReqDto dto); //회원가입
    boolean isDuplicatedEmail(String email); //이메일 중복 확인
    void editTmpPassword(String email, String tmpPass); //임시비밀번호로 변경
    boolean editPassword(String email, MemberEditPasswordReqDto dto);
    boolean isExistMember(MemberFindPwReqDto dto); //존재하는 회원인지 확인
    MemberFormDto getMemberInfo(String email); //멤버 정보 가져오기
    void editMember(MemberFormDto dto); //회원 정보 수정
}
