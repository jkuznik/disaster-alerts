package pl.ateam.disasteralerts.message;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class ToastMessage {

    @NotNull
    private ToastMessageType type;
    private String icon;
    @NotNull
    private String header;
    @NotNull
    private String message;
}
