package telran.monitoring.service;

import telran.monitoring.dto.EmailNotificationData;

public interface EmailDataProviderService {
	EmailNotificationData getEmailData(long patientId);
}
