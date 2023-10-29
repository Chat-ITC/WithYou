package WithYou.domain.ai.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChatGptRequestDto {
    private String model;
    private List<Message> messages;
    private int max_tokens;

    public ChatGptRequestDto(String model, String content) {        //매개변수에 학생 학과를 받아와서 사용하면 될듯
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "너는 대학교 교수야. 전공 관련 질문에 대해서 항상 정확한 답변을 주며 어떤 질문이든 반드시 대답할 수 있어."));
        //@authenticationprincipal을 이용해서 User의 학과를 불러온 후 해당 학과 교수로서 답을 하도록 역할 부여할 예정
        this.messages.add(new Message("user", content));
        this.max_tokens = 800;

    }
}
