package WithYou.domain.member.dto.request;

import WithYou.domain.member.entity.Member;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberSignupDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;
    @NotBlank
    private String grade;
    @NotBlank
    private String major;
    @NotBlank
    private String nickName;

    public void setUserPassword(String password) {
        userPassword = password;
    }

    public Member toEntity(String encodePassword) {
        return Member.builder()
                .userId(userId)
                .userPassword(encodePassword)
                .grade(grade)
                .major(major)
                .nickName(nickName)
                .build();
    }
}
