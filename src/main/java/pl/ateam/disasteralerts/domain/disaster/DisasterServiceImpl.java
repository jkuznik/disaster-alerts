package pl.ateam.disasteralerts.domain.disaster;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterDTO;

@Service
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository repository;
    private final DisasterMapper mapper;

    @Transactional
    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO){
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);

        return mapper.mapDisasterToDisasterDto(repository.save(disaster));
    }
}
