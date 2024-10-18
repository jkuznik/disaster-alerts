package pl.ateam.disasteralerts.domain.disaster;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterDTO;

@Mapper(componentModel = "spring")
interface DisasterMapper {
    Disaster mapDisasterDtoToDisasterEntity(DisasterDTO dto);
    Disaster mapDisasterAddDtoToDisaster(DisasterAddDTO dto);
    DisasterDTO mapDisasterToDisasterDto(Disaster disaster);
}

