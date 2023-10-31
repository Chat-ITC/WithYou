package WithYou.global.auth.dto;

import WithYou.domain.member.entity.Member;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberSignupDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;
    @NotNull
    private int grade;
    @NotBlank
    private String major;
    @NotBlank
    private String nickName;

    public Member toEntity(String encodePassword) {
        return Member.builder()
                .userId(userId)
                .userPassword(encodePassword)
                .grade(grade)
                .major(major)
                .nickName(nickName)
                .level(0.0)
                .build();
    }
}
