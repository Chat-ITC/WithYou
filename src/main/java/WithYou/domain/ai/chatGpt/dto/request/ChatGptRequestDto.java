//package WithYou.domain.ai.chatGpt.dto.request;
//
//import WithYou.domain.ai.chatGpt.dto.Message;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//public class ChatGptRequestDto {
//    private String model;
//    private List<Message> messages;
//    private int max_tokens;
//
//    public ChatGptRequestDto(String model, String content) {
//        this.model = model;
//        this.messages = new ArrayList<>();
//        this.messages.add(new Message("system","너는 유능한 IT 개발자야. IT에 관한 지식이 풍부하며 IT 관련 질문에 반드시 답할 수 있어."));
//        //@authenticationprincipal을 이용해서 User의 학과를 불러온 후 해당 학과 교수로서 답을 하도록 역할 부여할 예정
//        this.messages.add(new Message("user", content));
//        this.max_tokens = 800;
//
//    }
//}
