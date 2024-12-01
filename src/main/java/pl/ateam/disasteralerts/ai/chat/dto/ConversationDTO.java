package pl.ateam.disasteralerts.ai.chat.dto;

import java.util.List;

public record ConversationDTO(String question,
                              List<String> answers) {
}
