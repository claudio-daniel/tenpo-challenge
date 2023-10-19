package cl.tenpo.rest.api.service.impl;

import cl.tenpo.rest.api.mapper.PageMapper;
import cl.tenpo.rest.api.mapper.RequestHistoryMapper;
import cl.tenpo.rest.api.model.entity.RequestHistory;
import cl.tenpo.rest.api.model.request.RequestHistoryParams;
import cl.tenpo.rest.api.repository.jpa.RequestHistoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static cl.tenpo.rest.api.mock.request.RequestHistoryMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestHistoryServiceImplTest {
    @Spy
    private PageMapper pageMapper;

    @Spy
    private RequestHistoryMapper requestHistoryMapper;

    @Mock
    private RequestHistoryRepository requestHistoryRepository;

    private RequestHistoryServiceImpl requestHistoryService;

    private RequestHistory requestMock;

    @BeforeEach
    void setup() {
        requestMock = mockRequestHistory();

        requestHistoryMapper = Mappers.getMapper(RequestHistoryMapper.class);
        pageMapper = Mappers.getMapper(PageMapper.class);
        pageMapper.setRequestHistoryMapper(requestHistoryMapper);

        requestHistoryService = new RequestHistoryServiceImpl(requestHistoryRepository, pageMapper);
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

    @Test
    void whenFindAllRequestHistoryThenReturnHistoryResponse() {
        var requestHistoryParams = RequestHistoryParams.builder().size(1).page(1).build();
        var pageRequest = PageRequest.of(1, 1);
        var pageContent = Collections.singletonList(mockRequestHistoryWithId());
        var pageImpl = new PageImpl<>(pageContent, pageRequest, 2);

        when(requestHistoryRepository.findAll(pageRequest)).thenReturn(pageImpl);

        var requestHistoryResponse = requestHistoryService.findAllRequestHistory(requestHistoryParams);

        assertNotNull(requestHistoryResponse);
        assertEquals(pageImpl.getTotalElements(), requestHistoryResponse.getTotalElements());
        assertEquals(pageImpl.getTotalPages(), requestHistoryResponse.getTotalPages());
        assertEquals(pageImpl.getNumber(), requestHistoryResponse.getPage());

        verify(requestHistoryRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void whenFindAllRequestHistoryDoesNotWorkThenThenThrowException() {
        var requestHistoryParams = RequestHistoryParams.builder().size(1).page(1).build();
        var pageRequest = PageRequest.of(1, 1);
        var pageContent = Collections.singletonList(mockRequestHistoryWithId());
        var pageImpl = new PageImpl<>(pageContent, pageRequest, 2);

        when(requestHistoryRepository.findAll(pageRequest)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> requestHistoryService.findAllRequestHistory(requestHistoryParams));
        verify(requestHistoryRepository, times(1)).findAll(pageRequest);
    }
}