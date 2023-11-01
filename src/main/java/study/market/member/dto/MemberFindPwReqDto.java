package study.market.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberFindPwReqDto {

    @NotBlank(message = "필수 입력 값 입니다.")
    @Email(message = "이메일 형식으로 작성해주세요. ex) abcde@naver.com")
    private String email;

    @NotBlank(message = "필수 입력 값 입니다.")
    private String name;

}
