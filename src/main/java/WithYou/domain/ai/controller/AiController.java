package WithYou.domain.ai.controller;

import WithYou.domain.ai.dto.request.QuestionRequestDto;
import WithYou.domain.ai.dto.response.QuestionResponseDto;
import WithYou.domain.ai.entity.AiSummaryContent;
import WithYou.domain.ai.service.AiService;
import WithYou.domain.ai.service.ChatGptService;
import WithYou.domain.ai.service.OCRGeneralService;
import WithYou.global.jwt.MemberPrincipal;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AiController {
    private final OCRGeneralService ocrGeneralService;
    private final ChatGptService chatGptService;
    private final AiService aiService;

    @Value("${spring.ocr.url}")
    String apiURL;
    @Value("${spring.ocr.key}")
    String secretKey;

    @PostMapping("/ai/question")
    public ResponseEntity<?> askQuestionToChatGpt(
            @RequestParam("imageFile") MultipartFile multipartFile,
            @RequestParam("question") String question,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) throws IOException {
        File file = File.createTempFile("temp", null);
        multipartFile.transferTo(file);

        String ocrResult = ocrGeneralService.processImage(apiURL, secretKey, file.getPath());
        ocrGeneralService.checkStringExist(ocrResult);

        QuestionRequestDto questionRequestDto = aiService.makeQuestionRequestDto(ocrResult, question,
                memberPrincipal.getMember());

        QuestionResponseDto responseDto = chatGptService.askQuestion(questionRequestDto);
        aiService.saveSummaryContent(responseDto);

        return ResponseEntity.ok()
                .body("저장 완료");
    }

    @GetMapping("/ai/list")
    public ResponseEntity<?> getQuestionList(@AuthenticationPrincipal MemberPrincipal memberPrincipal
            , @PageableDefault(size = 5, direction = Direction.DESC, sort = "createAt") Pageable pageable) {
        Page<AiSummaryContent> aiSummaryContents = aiService.getAiSummaryContentsList(pageable,
                memberPrincipal.getMember());

        List<QuestionResponseDto> responseDtoList = aiSummaryContents.getContent()
                .stream()
                .map(QuestionResponseDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(responseDtoList);
    }
}
