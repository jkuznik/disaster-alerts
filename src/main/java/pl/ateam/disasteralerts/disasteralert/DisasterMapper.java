package pl.ateam.disasteralerts.disasteralert;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Mapper
interface DisasterMapper {
    Disaster mapDisasterDtoToDisasterEntity(DisasterDTO dto);
    Disaster mapDisasterAddDtoToDisaster(DisasterAddDTO dto);
    DisasterDTO mapDisasterToDisasterDto(Disaster disaster);
}

