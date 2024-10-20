package pl.ateam.disasteralerts.disaster;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;

@Mapper(componentModel = "spring")
interface DisasterMapper {
    Disaster mapDisasterDtoToDisasterEntity(DisasterDTO dto);
    Disaster mapDisasterAddDtoToDisaster(DisasterAddDTO dto);
    DisasterDTO mapDisasterToDisasterDto(Disaster disaster);
}

