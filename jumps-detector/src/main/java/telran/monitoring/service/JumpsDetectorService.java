package telran.monitoring.service;

import telran.monitoring.dto.*;

public interface JumpsDetectorService {
	JumpPulse processPulseProbe(PulseProbe pulse);
}
