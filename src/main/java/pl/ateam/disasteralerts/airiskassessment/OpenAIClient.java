package pl.ateam.disasteralerts.airiskassessment;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OpenAIClient {

    private final ChatModel chatModel;

    double getRiskScore(String prompt) {
        String response = chatModel.call(prompt);
        return Double.parseDouble(response);
    }
}
