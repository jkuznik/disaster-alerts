package pl.ateam.disasteralerts.disasteralert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterAddWebDTO;
import pl.ateam.disasteralerts.disasteralert.dto.DisasterDTO;

import java.time.Instant;

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
    public void addDisasterFromWeb(DisasterAddWebDTO disasterAddWebDTO) {
        Disaster disaster = mapper.mapDisasterAddWebDTOtoDisaster(disasterAddWebDTO);
        disaster.setSource("user");
        disaster.setStatus(DisasterStatus.ACTIVE);
        disaster.setDisasterStartTime(Instant.now());
        repository.save(disaster);
    }
}
