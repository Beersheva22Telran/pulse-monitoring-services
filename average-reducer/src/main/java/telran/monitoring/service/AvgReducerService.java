package telran.monitoring.service;

import telran.monitoring.dto.PulseProbe;

public interface AvgReducerService {
Integer reduce(PulseProbe probe);
}