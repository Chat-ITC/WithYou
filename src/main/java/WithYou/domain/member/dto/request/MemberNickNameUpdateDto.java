package WithYou.domain.member.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberNickNameUpdateDto {
    @NotBlank
    private String nickName;
}
