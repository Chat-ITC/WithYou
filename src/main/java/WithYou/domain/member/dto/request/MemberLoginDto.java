package WithYou.domain.member.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;
}
