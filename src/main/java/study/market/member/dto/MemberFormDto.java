package study.market.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class MemberFormDto {

    @Email(message = "이메일 형식으로 작성해주세요. ex) abcde@naver.com")
    @NotBlank(message = "필수 입력 값 입니다.")
    public String email;

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

    @Builder
    public MemberFormDto(String email, String name, String phoneNumber, String address, String detailAddress, String zipCode) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
    }
}
