package study.market.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.market.etc.entity.BaseTimeEntity;
import study.market.member.MemberStatus;
import study.market.member.Role;

/**
 * 회원 테이블
 * id : 기본 키
 * email : 로그인 시 필요한 아이디 / 회원 인증
 * password : 비밀번호
 * name : 이름
 * address : 주소
 * detailAddress : 상세 주소
 * zipCode : 우편번호
 * memberStatus : 회원 상태(활성화, 중지)
 * role : 회원 구분(관리자, 사용자)
 */

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String email, String password, String name, String phoneNumber, String address, String detailAddress, String zipCode, MemberStatus memberStatus, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.memberStatus = memberStatus;
        this.role = role;
    }

    //비밀번호 변경
    public void editPassword(String password) {
        this.password = password;
    }

    //주소 변경
    public void editAddress(String address, String detailAddress, String zipCode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
    }

}
