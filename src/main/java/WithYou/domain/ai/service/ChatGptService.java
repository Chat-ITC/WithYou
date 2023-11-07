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
        String major = questionRequestDto.getMajor();
        String ocrResult = questionRequestDto.getOcrResult();
        String question = questionRequestDto.getQuestion();

        String promptQuestion = major + "관련 질문이야. " + "다음 내용을 바탕으로 " + question +
                "만약 답을 할 수 없다면 왜 답을 할 수 없는지에 대한 이유를 설명해줘\n" + ocrResult;

        String content = askQuestionGpt(promptQuestion, major);

        String promptTitle = "다음 내용에 있는 키워드를 중점으로 간략하게 제목 만들어줘" + content;
        String title = askQuestionGpt(promptTitle, major);

        QuestionResponseDto questionResponseDto = new QuestionResponseDto(title, content);
        return questionResponseDto;
    }

    public String askQuestionGpt(String prompt, String major) {
        ChatGptRequestDto chatGptRequestDto = new ChatGptRequestDto(model, prompt, major);
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
