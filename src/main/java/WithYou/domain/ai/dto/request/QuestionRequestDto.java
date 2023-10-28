package WithYou.domain.ai.dto.request;

import WithYou.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class QuestionRequestDto {
    private String ocrResult;
    private String question;
    private String major;

    public QuestionRequestDto(String ocrResult, String question, Member member) {
        this.ocrResult = ocrResult;
        this.question = question;
        this.major = member.getMajor();
    }
}
