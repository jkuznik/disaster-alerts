package pl.ateam.disasteralerts.domain.alert;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.domain.alert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.domain.alert.dto.AlertDTO;

@Mapper(componentModel = "spring")
interface AlertMapper {
    AlertDTO mapAlertToAlertDto(Alert alert);
    Alert mapAlertDtoToAlert(AlertDTO alertDTO);
    Alert mapAlertAddDtoToAlert(AlertAddDTO alertAddDTO);
}
