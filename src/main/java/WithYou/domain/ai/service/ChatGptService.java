package WithYou.domain.ai.service;

import WithYou.domain.ai.dto.request.ChatGptRequestDto;
import WithYou.domain.ai.dto.request.Message;
import WithYou.domain.ai.dto.request.QuestionRequestDto;
import WithYou.domain.ai.dto.response.ChatGptChoiceResponseDto;
import WithYou.domain.ai.dto.response.ChatGptResponseDto;
import WithYou.domain.ai.dto.response.QuestionResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {

    @Value("${spring.openai.model}")
    private String model;

    @Value("${spring.openai.api.url}")
    private String apiUrl;

    @Value("${spring.openai.api.api-key}")
    private String openaiApiKey;

    private HttpEntity<ChatGptRequestDto> getHttpEntity(ChatGptRequestDto chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);
        HttpEntity<ChatGptRequestDto> httpRequest = new HttpEntity<>(chatRequest, headers);
        return httpRequest;
    }


    @Async
    public QuestionResponseDto askQuestion(QuestionRequestDto questionRequestDto) {
        String field = questionRequestDto.getField();
        String ocrResult = questionRequestDto.getOcrResult();
        String question = questionRequestDto.getQuestion();

        System.out.println("field = " + field);
        System.out.println("question = " + question);

        String promptQuestion = field + "관련 질문이야. " + "다음 내용을 바탕으로 " + question +
                "만약 질문에 답을 할 수 없다면 왜 답을 할 수 없는지에 대한 이유를 설명해주고 답변할 수 있는 질문의" +
                "대체 표현을 하나 이상 알려줘\n" + ocrResult;

        String content = askQuestionGpt(promptQuestion, field);

        String promptTitle = "다음 내용에 있는 키워드를 중점으로 간략하게 제목 만들어줘" + content;
        String title = askQuestionGpt(promptTitle, field);

        QuestionResponseDto questionResponseDto = new QuestionResponseDto(title, content);
        return questionResponseDto;
    }

    public String askQuestionGpt(String prompt, String field) {
        ChatGptRequestDto chatGptRequestDto = new ChatGptRequestDto(model, prompt, field);
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
