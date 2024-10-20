package pl.ateam.disasteralerts.disaster;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disaster.dto.DisasterDTO;
import pl.ateam.disasteralerts.disaster.enums.DisasterStatus;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class DisasterServiceImpl implements DisasterService {
    private final DisasterRepository repository;
    private final DisasterMapper mapper;

    @Transactional
    public DisasterDTO addDisaster(DisasterAddDTO disasterAddDTO){
        Disaster disaster = mapper.mapDisasterAddDtoToDisaster(disasterAddDTO);
        disaster.setStatus(DisasterStatus.ACTIVE);

        return mapper.mapDisasterToDisasterDto(repository.save(disaster));
    }

    @Override
    public DisasterDTO getDisasterById(UUID id) {
        return mapper.mapDisasterToDisasterDto(repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Disaster with id " + id + " not found")
        ));
    }
}
