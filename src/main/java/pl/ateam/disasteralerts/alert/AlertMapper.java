package pl.ateam.disasteralerts.alert;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.alert.dto.AlertDTO;

@Mapper(componentModel = "spring")
interface AlertMapper {
    AlertDTO mapAlertToAlertDto(Alert alert);
    Alert mapAlertDtoToAlert(AlertDTO alertDTO);
    Alert mapAlertAddDtoToAlert(AlertAddDTO alertAddDTO);
}
