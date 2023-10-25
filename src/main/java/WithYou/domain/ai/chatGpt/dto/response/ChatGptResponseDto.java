package WithYou.domain.ai.chatGpt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptResponseDto {

    private List<ChatGptChoiceResponseDto> choices;
    private Object Usage;
}
