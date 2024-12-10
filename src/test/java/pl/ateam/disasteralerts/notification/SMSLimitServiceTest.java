package pl.ateam.disasteralerts.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = {SMSLimitService.class})
@TestPropertySource(properties = "day.sms.limit=5")
class SMSLimitServiceTest {

    @Autowired
    SMSLimitService smsLimitService;

    @MockBean
    SMSLimitRepository smsLimitRepository;

    @Value("${day.sms.limit}")
    private String daySmsLimit;

    @Test
    void shouldReturnLimitNotReach() {
        //given
        SMSLimit smsLimit = SMSLimit.builder()
                .limitCounter(0)
                .build();

        //when
        when(smsLimitRepository.findByExactDay(any(LocalDateTime.class))).thenReturn(Optional.of(smsLimit));

        //then
        boolean belowLimit = smsLimitService.isBelowLimit(LocalDateTime.now());

        assertThat(belowLimit).isTrue();
    }

    @Test
    void shouldReturnLimitIsReach() {
        //given
        SMSLimit smsLimit = SMSLimit.builder()
                .limitCounter(Integer.parseInt(daySmsLimit))
                .build();

        //when
        when(smsLimitRepository.findByExactDay(any(LocalDateTime.class))).thenReturn(Optional.of(smsLimit));

        //then
        boolean belowLimit = smsLimitService.isBelowLimit(LocalDateTime.now());

        assertThat(belowLimit).isFalse();
    }

    @Test
    void shouldCreateNewLimiter_whenNeededLimiterNotExist() {
        //given
        LocalDateTime exactDay = LocalDateTime.now();

        //when
        when(smsLimitRepository.findByExactDay(exactDay)).thenReturn(Optional.empty());

        //then
        smsLimitService.isBelowLimit(exactDay);

        verify(smsLimitRepository, times(1)).save(any(SMSLimit.class));
    }

    @Test
    void shouldIncreaseLimitCounter_whenLimiterExistAndIsBelowLimit() {
        //given
        LocalDateTime exactDay = LocalDateTime.now();
        SMSLimit smsLimit = SMSLimit.builder()
                .limitCounter(5)
                .build();

        int limitCounterBeforeIncrease = smsLimit.getLimitCounter();

        //when
        when(smsLimitRepository.findByExactDay(any(LocalDateTime.class))).thenReturn(Optional.of(smsLimit));

        //then
        int limitCounterAfterIncrease = smsLimitService.increaseLimit(exactDay);

        assertThat(limitCounterBeforeIncrease).isEqualTo(limitCounterAfterIncrease);
    }
}