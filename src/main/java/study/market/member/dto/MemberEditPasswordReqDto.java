package study.market.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberEditPasswordReqDto {

    @NotBlank
    public String password;

    @NotBlank
    public String confirmPassword;
}
