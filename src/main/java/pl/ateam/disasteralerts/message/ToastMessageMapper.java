package pl.ateam.disasteralerts.message;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.message.dto.ToastMessageDTO;

@Mapper(componentModel = "spring")
interface ToastMessageMapper {
    ToastMessageDTO mapToastMessageToToastMessageDTO(ToastMessage toastMessage);
}
