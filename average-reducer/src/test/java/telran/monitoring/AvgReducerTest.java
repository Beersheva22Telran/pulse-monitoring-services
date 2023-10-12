package telran.monitoring;

import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import telran.monitoring.dto.PulseProbe;
import telran.monitoring.entities.ProbesList;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class AvgReducerTest {
	static final long PATIENT_ID_NO_REDIS = 123L;
	static final long PATIENT_ID_NO_AVG = 124l;
	static final long PATIENT_ID_AVG = 125L;
	static final int VALUE1 = 100;
	static final int VALUE2 = 120;
	static final int AVG_VALUE = (VALUE1 + VALUE2) / 2;
	static PulseProbe probeNoRedis = new PulseProbe(PATIENT_ID_NO_REDIS, VALUE1, 0, 0);
	static PulseProbe probeNoAvg = new PulseProbe(PATIENT_ID_NO_AVG, VALUE1, 0, 0);
	static PulseProbe probeAvg = new PulseProbe(PATIENT_ID_NO_REDIS, VALUE2, 0, 0);
	static ProbesList probsListNoAvg = new ProbesList(PATIENT_ID_NO_AVG);
	static ProbesList probsListAvg = new ProbesList(PATIENT_ID_AVG);
	static List<Integer> emptyList;
	static List<Integer> oneValueList;
	
	

}
