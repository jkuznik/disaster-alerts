package pl.ateam.disasteralerts.disasteralert;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.disasteralert.dto.AlertAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.AlertDTO;

@Mapper(componentModel = "spring")
interface AlertMapper {
    AlertDTO mapAlertToAlertDto(Alert alert);
    Alert mapAlertDtoToAlert(AlertDTO alertDTO);
    Alert mapAlertAddDtoToAlert(AlertAddDTO alertAddDTO);
}
