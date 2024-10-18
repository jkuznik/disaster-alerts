package pl.ateam.disasteralerts.domain.disaster;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.domain.disaster.dto.DisasterDTO;

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

        return mapper.mapDisasterToDisasterDto(repository.save(disaster));
    }

    @Override
    public DisasterDTO getDisasterById(UUID id) {
        return mapper.mapDisasterToDisasterDto(repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Disaster with id " + id + " not found")
        ));
    }
}
