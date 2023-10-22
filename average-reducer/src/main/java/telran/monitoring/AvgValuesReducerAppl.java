package telran.monitoring;

import java.util.function.Consumer;


import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.*;
import telran.monitoring.service.AvgReducerService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AvgValuesReducerAppl {
	final AvgReducerService service;
	final StreamBridge streamBridge;
	@Value("${app.avg.binding.name}")
	String bindingName;
	public static void main(String[] args) {
		SpringApplication.run(AvgValuesReducerAppl.class, args);

	}
	@Bean
	Consumer<PulseProbe> pulseProbeConsumerAvg() {
		return this::processPulseProbe;
	}
	void processPulseProbe(PulseProbe probe) {
		log.trace("{}", probe);
		long patientId = probe.patientId();
		Integer avgValue = service.reduce(probe);
		if (avgValue != null) {
			log.debug("for patient {} avg value is {}", patientId, avgValue);
			streamBridge.send(bindingName, new PulseProbe(patientId,avgValue, System.currentTimeMillis(), 0));
		} else {
			log.trace("for patient {} no avg value yet", patientId);
		}
		
	}
	
}
