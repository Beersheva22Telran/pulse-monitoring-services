package telran.monitoring.documents;

import java.time.*;
import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.api.*;
@Document(collection = ApiConstants.avgValuesCollection)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AvgPulseDoc {
	long patientId;
	int value;
	LocalDateTime dateTime;
	public static AvgPulseDoc of(PulseProbe pulseProbe) {
		return new AvgPulseDoc(pulseProbe.patientId(),
				pulseProbe.value(), LocalDateTime.ofInstant(Instant.ofEpochMilli(pulseProbe.timestamp()),
								ZoneId.systemDefault()));
	}
	@Override
	public int hashCode() {
		return Objects.hash(patientId, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvgPulseDoc other = (AvgPulseDoc) obj;
		return patientId == other.patientId && value == other.value;
	}
	public long getPatientId() {
		return patientId;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public int getValue() {
		return value;
	}
	
}
