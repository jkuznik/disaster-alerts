package pl.ateam.disasteralerts.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.context.annotation.Configuration;
import pl.ateam.disasteralerts.ai.chat.dto.ConversationDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Configuration
class OpenAiConfig {

    private final ChatClient chatClient;
    private final List<Message> messages = new ArrayList<>();

    public OpenAiConfig(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    ConversationDTO getAnswer(String question) {

        return chatClient.prompt()
                .system(getSystemMessage().getContent())
                .user(question)
                .call()
                .entity(ConversationDTO.class);
    }

    DisasterAddDTO getDisasterAdd(UUID uuid) {
        return chatClient.prompt()
                .system("Zbuduj odpowiedź na podstawie konwersacji")
                .messages(messages)
                .user("userId: " + uuid)
                .call()
                .entity(DisasterAddDTO.class);
    }

    void addMessage(Message message) {
        messages.add(message);
    }

    List<Message> getMessages() {
        return messages;
    }

    private Message getSystemMessage() {
        String stringPrompt = """
                Jesteś wirtualnym asystentem do przyjmowania zgłoszeń o awariach i zagrożeniach naturalnych od użytkowników. Twoim zadaniem jest przeprowadzenie użytkownika przez proces zgłoszenia, zadawanie precyzyjnych pytań w celu uzyskania wszystkich kluczowych informacji o zgłoszeniu, a następnie wygenerowanie odpowiedzi w formacie JSON na podstawie uzyskanych danych.
                
                Podczas rozmowy:
                - Zidentyfikuj typ zgłoszenia: Na początku rozmowy ustal, czy użytkownik zgłasza awarię (np. wodociągową, elektryczną, drogową) czy zagrożenie naturalne (np. powódź, pożar, osunięcie ziemi).
                - Pytania o szczegóły: Zadawaj pytania, aby uzyskać szczegółowe lub ogólne informacje o:
                  - Skali problemu
                  - Widocznych skutkach
                  - Ewentualnej obecności służb ratunkowych na miejscu
                  - Dodatkowych istotnych szczegółach
                
                Zadawaj pytania pojedynczo, w formie `question` oraz `answers`.
                Użytkownik będzie odpowiadał zaproponowanymi możliwościami.
                Pytanie i odpowiedź podaj w formie JSON: {conversation} (String question, List<String> answers)
                Jeśli nie ma żadnych odpowiednich odpowiedzi wygeneruj podsumowanie w question i zadnych odpowiedzi.
                
                Pamiętaj, by rozmowa była zwięzła i jasna, aby użytkownik wiedział, jakie informacje są potrzebne. JSON ma być przejrzysty i zawierać tylko najistotniejsze informacje o zgłoszeniu.
                Do kontekstu dołączam wiadomości, które wcześniej wymieniłeś z użytkownikiem: {messages}.
                """;

        return new SystemPromptTemplate(stringPrompt)
                .createMessage(Map.of(
                        "conversation", ConversationDTO.class,
                        "messages", messages
                ));
    }
}
