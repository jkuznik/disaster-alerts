package pl.ateam.disasteralerts.disasteralert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Service
class DisasterService {

    private final DisasterRepository repository;
    DisasterMapper disasterMapper = Mappers.getMapper(DisasterMapper.class);

    public DisasterService(DisasterRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DisasterDTO addDisaster(@NotNull @Valid DisasterAddDTO disasterAddDTO){

        return disasterMapper.disasterToDto(
                repository.save(disasterMapper.disasterAddDtoToDisaster(disasterAddDTO)
                ));
    }
}
