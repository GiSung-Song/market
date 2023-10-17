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

    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    @Min(2) @Max(4)
    public String name;

    @NotBlank
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    public String phoneNumber;

    @NotBlank
    public String address;

    @NotBlank
    @Length(min = 6, max = 6)
    public String zipCode;

}
