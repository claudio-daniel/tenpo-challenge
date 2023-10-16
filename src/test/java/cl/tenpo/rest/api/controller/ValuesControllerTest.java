package cl.tenpo.rest.api.controller;

import cl.tenpo.rest.api.model.exception.ClientResourceAccessException;
import cl.tenpo.rest.api.service.ValuesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import static cl.tenpo.rest.api.mock.value.CalculateValueResponseMock.mockCalculatedValueResponse;
import static cl.tenpo.rest.api.mock.value.CalculateValueRequestMock.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = ValuesController.class)
public class ValuesControllerTest {

    private final String calculateValueEndpoint = "/tenpo/api-rest/values/calculate";

    @MockBean
    private ValuesService valuesService;

    @Autowired
    private MockMvc mvc;

    @Test
    void whenCalculateValueIsCalledShouldBeReturnCalculatedValueOK() throws Exception {

        when(valuesService.sumAndApplyPercentage(mockCalculateValueRequest())).thenReturn(mockCalculatedValueResponse());

        this.mvc.perform(post(calculateValueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCalculateValueRequestJson()))
                .andExpect(status().isOk());

        verify(valuesService, times(1)).sumAndApplyPercentage(mockCalculateValueRequest());
    }

    @Test
    void whenCalculateValueIsCalledWithoutBodyShouldBeReturnBadRequest() throws Exception {

        this.mvc.perform(post(calculateValueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockBadCalculateValueRequestJson()))
                .andExpect(status().isBadRequest());

        verify(valuesService, times(0)).sumAndApplyPercentage(any());
    }

    @Test
    void whenCalculateValueServiceFailShouldBeReturnInternalServerError() throws Exception {
        when(valuesService.sumAndApplyPercentage(mockCalculateValueRequest())).thenThrow(ClientResourceAccessException.class);

        this.mvc.perform(post(calculateValueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCalculateValueRequestJson()))
                .andExpect(status().isInternalServerError());

        verify(valuesService, times(1)).sumAndApplyPercentage(mockCalculateValueRequest());
    }
}