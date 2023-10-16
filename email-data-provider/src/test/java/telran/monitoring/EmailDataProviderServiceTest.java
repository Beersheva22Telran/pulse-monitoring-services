package telran.monitoring;
//insert into persons(id, email, name, dtype) values(123, 'vasya@gmail.com', 'Vasya', 'Doctor');
//insert into persons(id, email, name, dtype) values(124, 'petya@gmail.com', 'Petya', 'Doctor');
//insert into persons(id, email, name, dtype) values(125, 'olya@gmail.com', 'Olya', 'Patient');
//insert into persons(id, email, name, dtype) values(126, 'sara@gmail.com', 'Sara', 'Patient');
//insert into visits(doctor_id, patient_id, date) values(123, 125, '2023-10-10');
//insert into visits(doctor_id, patient_id, date) values(124, 125, '2023-10-12');
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.service.EmailDataProviderService;
@SpringBootTest
@Sql(scripts = "doctors-patients-visits.sql")
class EmailDataProviderServiceTest {
private static final long PATIENT_ID = 125l;
private static final long PATIENT_ID_NO_DOCTOR = 126l;
@Autowired
EmailDataProviderService service;
EmailNotificationData data = new EmailNotificationData("petya@gmail.com", "Petya", "Olya");
	@Test
	void doctorPatientExistTest() {
		assertEquals(data, service.getEmailData(PATIENT_ID));
	}
	@Test
	void patientNotFoundTest() {
		assertThrowsExactly(RuntimeException.class, ()->service.getEmailData(10000), "patient not found");
	}
	@Test
	void doctorNotFoundTest() {
		assertThrowsExactly(RuntimeException.class, ()->service.getEmailData(PATIENT_ID_NO_DOCTOR), "doctor not found");
	}

}
