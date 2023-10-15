package telran.monitoring.service;

import telran.monitoring.dto.EmailNotificationData;

public interface EmailDataProvider {
	EmailNotificationData getData(long patientId);
}
