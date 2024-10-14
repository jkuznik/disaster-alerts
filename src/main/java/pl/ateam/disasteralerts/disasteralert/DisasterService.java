package pl.ateam.disasteralerts.disasteralert;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

@Service
class DisasterService implements DisasterServiceAPI{

    private final DisasterRepository repository;
    DisasterMapper disasterMapper = Mappers.getMapper(DisasterMapper.class);

    public DisasterService(DisasterRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO){

        return disasterMapper.disasterToDto(
                repository.save(disasterMapper.disasterAddDtoToDisaster(disasterAddDTO)
                ));
    }
}
