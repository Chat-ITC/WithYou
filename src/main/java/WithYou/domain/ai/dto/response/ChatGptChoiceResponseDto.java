package WithYou.domain.ai.dto.response;

import WithYou.domain.ai.dto.request.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptChoiceResponseDto {
    private int index;
    private Message message;
}

