package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.controller.EmailDataProviderController;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.service.EmailDataProviderService;


@WebMvcTest(value= {EmailDataProviderController.class})
class EmailDataProviderControllerTest {
	static final long PATIENT_ID_NORMAL=125l;
    static final EmailNotificationData data = new EmailNotificationData("doctor@gmail.com", "doctor", "patient125");
    static final String ERROR_RESPONSE = "patient not found";
	private static final long PATIENT_ID_NOT_EXIST = 123;
@Autowired
MockMvc mockMvc;
@Autowired
ObjectMapper mapper;
@MockBean
EmailDataProviderService service;
@BeforeEach
void serviceMocking() {
	when(service.getEmailData(PATIENT_ID_NORMAL)).thenReturn(data);
	when(service.getEmailData(PATIENT_ID_NOT_EXIST)).thenThrow(new RuntimeException(ERROR_RESPONSE));
}
	@Test
	@DisplayName("service returns data")
	void dataExistFlow() throws Exception {
		String responseJSON = mockMvc.perform(get("http://localhost:8080/data/" + PATIENT_ID_NORMAL)).andDo(print()).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		assertEquals(mapper.readValue(responseJSON, EmailNotificationData.class), data);
	}
	@Test
	@DisplayName("service throws exception")
	void noDataFlow() throws Exception {
		String response = mockMvc.perform(get("http://localhost:8080/data/" + PATIENT_ID_NOT_EXIST)).andDo(print()).andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString();
		assertEquals(response, ERROR_RESPONSE);
	}

}
