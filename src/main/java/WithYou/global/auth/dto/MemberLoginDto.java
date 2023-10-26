package WithYou.global.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;
}
