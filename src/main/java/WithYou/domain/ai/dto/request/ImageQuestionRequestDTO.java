package WithYou.domain.ai.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageQuestionRequestDTO {
    private MultipartFile imageFile;
    @NotBlank
    private String question;
    @NotBlank
    private String major;
    @NotBlank
    private String grade;
}
