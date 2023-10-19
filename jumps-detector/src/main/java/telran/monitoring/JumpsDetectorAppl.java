package telran.monitoring;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.*;
import telran.monitoring.service.JumpsDetectorService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JumpsDetectorAppl {
final StreamBridge streamBridge;
final JumpsDetectorService jumpsService;
@Value("${app.jumps.binding.name}")
String jumpsBindingName;
	public static void main(String[] args) {
		SpringApplication.run(JumpsDetectorAppl.class, args);

	}
	@Bean
Consumer<PulseProbe> pulseProbeConsumerJumps() {
	return this::probeConsumer;
}
void probeConsumer(PulseProbe pulseProbe) {
	log.trace("received {}", pulseProbe);
	JumpPulse jump = jumpsService.processPulseProbe(pulseProbe);
	if(jump != null) {
		streamBridge.send(jumpsBindingName, jump);
		log.debug("jump {} sent to {}", jump, jumpsBindingName);
	} else {
		log.trace("no jump sent");
	}
	
    
}


}
