package WithYou.domain.ai.dto.request;

import WithYou.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class QuestionRequestDto {
    private String ocrResult;
    private String question;
    private String field;       // 분야 추가

    public QuestionRequestDto(String ocrResult, String question, String field) {
        this.ocrResult = ocrResult;
        this.question = question;
        this.field = field;
    }
}
