package WithYou.domain.ai.controller;

import WithYou.domain.ai.dto.request.QuestionRequestDto;
import WithYou.domain.ai.service.ChatGptService;
import WithYou.domain.ai.service.OCRGeneralService;
import WithYou.global.jwt.MemberPrincipal;
import java.io.File;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiController {
    private final OCRGeneralService ocrGeneralService;
    private final ChatGptService chatGptService;

    @Value("${spring.ocr.url}")
    String apiURL;
    @Value("${spring.ocr.key}")
    String secretKey;

    @PostMapping("/ai/question")
    public ResponseEntity<?> askQuestionToChatGpt(
            @RequestBody @Valid QuestionRequestDto question,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) throws IOException {
        File file = File.createTempFile("temp", null);
        question.getImageFile().transferTo(file);

        String ocr_result = ocrGeneralService.processImage(apiURL, secretKey, file.getPath());
        ocrGeneralService.checkStringExist(ocr_result);


    }
}
