package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import telran.monitoring.dto.*;
import telran.monitoring.entity.LastPulseValue;
import telran.monitoring.repo.LastPulseValueRepo;
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class JumpsDetectorTest {
static final long PATIENT_ID_NO_REDIS = 123l;
static final long PATIENT_ID_NO_JUMP = 124l;
static final long PATIENT_ID_JUMP = 125l;
private static final int VALUE = 100;
private static final int JUMP_VALUE = 140;
InputDestination producer;
OutputDestination consumer;
PulseProbe probeNoRedis = new PulseProbe(PATIENT_ID_NO_REDIS, VALUE, 0, 0);
PulseProbe probeNoJump = new PulseProbe(PATIENT_ID_NO_JUMP, VALUE, 0, 0);
PulseProbe probeJump = new PulseProbe(PATIENT_ID_JUMP, JUMP_VALUE, 0, 0);
LastPulseValue noJumpValue = new LastPulseValue(PATIENT_ID_NO_JUMP, VALUE);
LastPulseValue jumpValue = new LastPulseValue(PATIENT_ID_JUMP, VALUE);
JumpPulse jump = new JumpPulse(PATIENT_ID_JUMP, VALUE, JUMP_VALUE);
String consumerBindingName = "";
@MockBean
LastPulseValueRepo lastPulseRepo;
@BeforeEach
void redisMocking() {
	when(lastPulseRepo.findById(PATIENT_ID_NO_REDIS)).thenReturn(Optional.ofNullable(null));
	when(lastPulseRepo.findById(PATIENT_ID_NO_JUMP)).thenReturn(Optional.of(noJumpValue));
	when(lastPulseRepo.findById(PATIENT_ID_JUMP)).thenReturn(Optional.of(jumpValue));
}
	@Test
	void noRedisTest() {
		
		producer.send(new GenericMessage<PulseProbe>(probeNoRedis), consumerBindingName);
	}

}
