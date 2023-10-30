package WithYou.domain.post.entity;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    private String userNickName;
    private String userMajor;
    private int userGrade;
}
