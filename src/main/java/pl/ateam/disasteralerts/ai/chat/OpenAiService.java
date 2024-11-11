package pl.ateam.disasteralerts.ai.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.ai.chat.dto.ConversationDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final OpenAiConfig openAiConfig;

    public ConversationDTO getResponse(String answerFromUser) {

        ConversationDTO answer = openAiConfig.getAnswer(answerFromUser);

        addToMessages(answerFromUser, answer);

        return answer;
    }

    public String getDisasterAdd() {
        return openAiConfig.getDisasterAdd();
    }

    private void addToMessages(String answerFromUser, ConversationDTO answer) {
        openAiConfig.addMessage(new UserMessage(answerFromUser));
        if (answer != null) {
            openAiConfig.addMessage(new AssistantMessage(answer.toString()));
        }
    }
}
