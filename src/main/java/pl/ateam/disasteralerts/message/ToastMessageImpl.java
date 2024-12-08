package pl.ateam.disasteralerts.message;

import org.springframework.stereotype.Service;
import pl.ateam.disasteralerts.message.dto.ToastMessageDTO;

@Service
class ToastMessageImpl implements ToastMessageFacade {

    private final ToastMessageMapper toastMessageMapper;

    public ToastMessageImpl(ToastMessageMapper toastMessageMapper) {
        this.toastMessageMapper = toastMessageMapper;
    }

    @Override
    public ToastMessageDTO buildMessage(ToastMessageType type, String header, String message) {
        String icon = getDefaultIcon(type);
        return buildMessage(type, icon, header, message);
    }

    @Override
    public ToastMessageDTO buildMessage(ToastMessageType type, String icon, String header, String message) {
        ToastMessage built = ToastMessage.builder()
                .type(type)
                .icon(icon)
                .header(header)
                .message(message)
                .build();
        return toastMessageMapper.mapToastMessageToToastMessageDTO(built);
    }

    private String getDefaultIcon(ToastMessageType type) {
        String icon = "bi bi-balloon-heart-fill";
        switch (type) {
            case DANGER -> icon = "bi bi-exclamation-circle-fill";
            case SUCCESS -> icon = "bi bi-check2-square";
        }
        return icon;
    }
}
