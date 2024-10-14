package pl.ateam.disasteralerts.disasteralert;

import org.mapstruct.Mapper;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Mapper
interface DisasterMapper {

    Disaster dtoToDisaster(DisasterDTO dto);
    Disaster disasterAddDtoToDisaster(DisasterAddDTO dto);
    DisasterDTO disasterToDto(Disaster disaster);



}
