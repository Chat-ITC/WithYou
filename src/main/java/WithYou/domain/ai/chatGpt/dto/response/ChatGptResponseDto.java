package WithYou.domain.ai.chatGpt.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptResponseDto {

    private List<ChatGptChoiceResponseDto> choices;
    private Object Usage;
}
