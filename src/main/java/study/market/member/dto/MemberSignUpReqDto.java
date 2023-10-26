package study.market.member.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignUpReqDto {

    @Email(message = "이메일 형식으로 작성해주세요. ex) abcde@naver.com")
    @NotBlank(message = "필수 입력 값 입니다.")
    public String email;

    @NotBlank(message = "필수 입력 값 입니다.")
    public String password;

    @NotBlank(message = "필수 입력 값 입니다.")
    public String passwordConfirm;

    @NotBlank(message = "필수 입력 값 입니다.")
    @Size(min = 2, max = 6, message = "이름은 2 ~ 6자 사이로 입력해주세요.")
    public String name;

    @NotBlank(message = "필수 입력 값 입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "ex) 010-1234-5678")
    public String phoneNumber;

    @NotBlank(message = "필수 입력 값 입니다.")
    public String address;

    @NotBlank(message = "필수 입력 값 입니다.")
    public String detailAddress;

    @NotBlank(message = "필수 입력 값 입니다.")
    @Length(min = 5, max = 5, message = "우편번호는 5자리입니다.")
    public String zipCode;

    public String authCode;

}
