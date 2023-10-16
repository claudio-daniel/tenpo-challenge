package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.repository.redis.PercentageHistoryRepository;
import cl.tenpo.rest.api.service.PercentageHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static cl.tenpo.rest.api.mock.value.PercentageMock.mockLastPercentageReceived;
import static cl.tenpo.rest.api.mock.value.PercentageMock.mockLastPercentageReturned;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PercentageHistoryServiceImplTest {

    private final String lastPercentageKey = "last-percentage-received";

    @Mock
    private PercentageHistoryRepository percentageHistoryRepository;

    private PercentageHistoryService percentageHistoryService;

    @BeforeEach
    void setup() {
        this.percentageHistoryService = new PercentageHistoryServiceImpl(percentageHistoryRepository);
    }

    @Test
    void whenLastPercentageExistsThenReturnPercentageValue() {
        when(percentageHistoryRepository.findById(lastPercentageKey)).thenReturn(Optional.of(mockLastPercentageReceived()));

        var lastPercentage = percentageHistoryService.findLastPercentageReceived();

        assertNotNull(lastPercentage);
        assertTrue(lastPercentage.isPresent());
        assertEquals(10, lastPercentage.get().getPercentageValue());

        verify(percentageHistoryRepository, times(1)).findById(lastPercentageKey);
    }

    @Test
    void whenLastPercentageDoesNotExistThenReturnEmpty() {
        when(percentageHistoryRepository.findById(lastPercentageKey)).thenReturn(Optional.empty());

        var lastPercentage = percentageHistoryService.findLastPercentageReceived();

        assertNotNull(lastPercentage);
        assertTrue(lastPercentage.isEmpty());

        verify(percentageHistoryRepository, times(1)).findById(lastPercentageKey);
    }

    @Test
    void whenSaveFinishesOkThenDoNothing() {
        var lastPercentageReceived = mockLastPercentageReceived();
        var lastPercentageReturned = mockLastPercentageReturned();

        when(percentageHistoryRepository.save(lastPercentageReceived)).thenReturn(lastPercentageReceived);
        when(percentageHistoryRepository.save(lastPercentageReturned)).thenReturn(lastPercentageReturned);

        percentageHistoryService.savePercentageHistory(lastPercentageReceived.getPercentageValue());

        verify(percentageHistoryRepository, times(1)).save(lastPercentageReceived);
        verify(percentageHistoryRepository, times(1)).save(lastPercentageReturned);

    }

    @Test
    void whenSaveDoesNotFinishesThenThrowException() {
        var lastPercentageReceived = mockLastPercentageReceived();
        when(percentageHistoryRepository.save(lastPercentageReceived)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> percentageHistoryService.savePercentageHistory(lastPercentageReceived.getPercentageValue()));

        verify(percentageHistoryRepository, times(1)).save(lastPercentageReceived);
    }
}