package WithYou.domain.member.dto.request;


import WithYou.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberMypageCheckDto {
    @NotBlank
    private String nickName;
    @NotBlank
    private double level;

    public static MemberMypageCheckDto of(Member member) {
        return new MemberMypageCheckDto(member.getNickName(), member.getLevel());
    }
}
