package pl.ateam.disasteralerts.message;

import org.springframework.stereotype.Component;
import pl.ateam.disasteralerts.message.dto.ToastMessageDTO;

@Component
public interface ToastMessageFacade {

    ToastMessageDTO buildMessage(ToastMessageType type, String header, String message);
    ToastMessageDTO buildMessage(ToastMessageType type, String icon, String header, String message);
}
