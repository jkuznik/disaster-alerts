package pl.ateam.disasteralerts.message.dto;

public record ToastMessageDTO(String type,
                              String icon,
                              String header,
                              String message) {
}
