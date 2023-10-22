package telran.monitoring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.JumpPulse;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.entity.LastPulseValue;
import telran.monitoring.repo.LastPulseValueRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class JumpsDetoctorServiceImpl implements JumpsDetectorService {
	final LastPulseValueRepo lastValueRepo;
	@Value("${app.jumps.threshold:0.3}")
	double jumpThreshold;

	@Override
	public JumpPulse processPulseProbe(PulseProbe pulseProbe) {
		LastPulseValue lastValue = lastValueRepo.findById(pulseProbe.patientId()).orElse(null);
		JumpPulse res = null;
		if(lastValue != null && isJump(pulseProbe.value(), lastValue.getValue())) {
			res = new JumpPulse(pulseProbe.patientId(), lastValue.getValue(), pulseProbe.value());
		} else if(lastValue == null){
			log.debug("no record in redis");
		} else {
			log.trace(" record in redis exists but no jump");
		}
		lastValue = new LastPulseValue(pulseProbe.patientId(), pulseProbe.value());
		lastValueRepo.save(lastValue);
		return res;
	}

	private boolean isJump(int currentValue, int prevValue) {
		int delta = Math.abs(currentValue - prevValue);
		
		return prevValue * jumpThreshold <= delta;
	}

}
