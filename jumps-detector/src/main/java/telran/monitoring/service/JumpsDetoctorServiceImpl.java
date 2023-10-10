package telran.monitoring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.monitoring.dto.JumpPulse;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.entity.LastPulseValue;
import telran.monitoring.repo.LastPulseValueRepo;
@Service
@RequiredArgsConstructor
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
		}
		lastValue = new LastPulseValue(pulseProbe.patientId(), pulseProbe.value());
		return res;
	}

	private boolean isJump(int currentValue, int prevValue) {
		int delta = Math.abs(currentValue - prevValue);
		return currentValue * jumpThreshold <= delta;
	}

}
