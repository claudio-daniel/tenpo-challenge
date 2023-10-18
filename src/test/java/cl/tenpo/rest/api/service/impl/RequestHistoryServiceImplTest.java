package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.repository.jpa.RequestHistoryRepository;
import cl.tenpo.rest.api.service.RequestHistoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static cl.tenpo.rest.api.mock.request.RequestHistoryMock.mockRequestHistory;
import static cl.tenpo.rest.api.mock.request.RequestHistoryMock.mockRequestHistoryWithId;

@ExtendWith(MockitoExtension.class)
public class RequestHistoryServiceImplTest {

    @Mock
    private RequestHistoryRepository requestHistoryRepository;

    private RequestHistoryService requestHistoryService;

    private RequestHistory requestMock;

    @BeforeEach
    void setup() {
        this.requestHistoryService = new RequestHistoryServiceImpl(requestHistoryRepository);
        this.requestMock = mockRequestHistory();
    }

    @Test
    void whenSaveRequestHistoryFinishesOkThenSaveData() {
        when(requestHistoryRepository.save(requestMock)).thenReturn(mockRequestHistoryWithId());

        requestHistoryService.save(requestMock);

        verify(requestHistoryRepository, times(1)).save(requestMock);
    }

    @Test
    void whenSaveRequestHistoryDoNotWorkThenThrowException() {
        when(requestHistoryRepository.save(requestMock)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> requestHistoryService.save(requestMock));
        verify(requestHistoryRepository, times(1)).save(requestMock);
    }
}