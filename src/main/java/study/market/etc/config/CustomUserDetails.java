package study.market.etc.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.market.member.enumType.MemberStatus;
import study.market.member.entity.Member;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    //사용자 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRole().toString()));
    }

    //사용자 비밀번호
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    //사용자 식별 이름 (이메일을 사용함)
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 가능 여부
    @Override
    public boolean isEnabled() {

        if (member.getMemberStatus() == MemberStatus.ACTIVE) {
            return true;
        } else {
            return false;
        }

    }
}
