package WithYou.domain.member.dto.response;


import WithYou.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberMyPageDto {
    @NotBlank
    private String nickName;
    @NotBlank
    private String major;
    @NotBlank
    private int grade;
    @NotBlank
    private double level;

    public static MemberMyPageDto of(Member member) {
        return new MemberMyPageDto(member.getNickName(), member.getMajor(), member.getGrade(), member.getLevel());
    }
}
