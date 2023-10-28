package WithYou.domain.ai.service;

import WithYou.domain.ai.dto.request.ChatGptRequestDto;
import WithYou.domain.ai.dto.request.Message;
import WithYou.domain.ai.dto.response.ChatGptChoiceResponseDto;
import WithYou.domain.ai.dto.response.ChatGptResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

public class ChatGptService {
    @Value("${spring.openai.model}")        //gpt-3.5-turbo
    private String model;

    @Value("${spring.openai.api.url}")  //https://api.openai.com/v1/chat/completions
    private String apiUrl;

    @Value("${spring.openai.api.api-key}")      //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
    private String openaiApiKey;

    private HttpEntity<ChatGptRequestDto> getHttpEntity(ChatGptRequestDto chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);
        HttpEntity<ChatGptRequestDto> httpRequest = new HttpEntity<>(chatRequest, headers);
        return httpRequest;
    }


    @Async
    public String askQuestion(String ocr_result, String question, String fav_language) {
        String prompt_question = "분야는 " + fav_language + " 입니다. 분야가 만약 '없음'이라면 코딩 언어에 국한되지 않고 내용에 맞게 대답해주세요"
                + "이제 다음 내용을 " + question + "이때 만약 '상관없음'이거나 질문에 대답하기 어렵다면 내용을 요약해줘. \n" + ocr_result;
        String messageTexts = askQuestion_gpt(prompt_question);

        String prompt_title = "다음 내용에 어울리는 제목 달아줘" + messageTexts;
        String messageText_title = askQuestion_gpt(prompt_title);

        return "";
    }
    //주석 --> History repository가 만들어지고, History ResponseDto가 만들어지면 대체될 부분

    public String askQuestion_gpt(String prompt) {
        ChatGptRequestDto chatGptRequestDto = new ChatGptRequestDto(model, prompt);
        RestTemplate restTemplate = new RestTemplate();
        ChatGptResponseDto response = restTemplate.postForObject(apiUrl, getHttpEntity(chatGptRequestDto),
                ChatGptResponseDto.class);

        List<ChatGptChoiceResponseDto> choiceList = response.getChoices();

        List<String> messageTexts = choiceList.stream().map(ChatGptChoiceResponseDto::getMessage)
                .map(Message::getContent)
                .collect(Collectors.toList());

        return String.join("\n", messageTexts);
    }
}
