package telran.monitoring.documents;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "avg-pules-values")
public class AvgPulseDoc {
	long patientId;
	int value;
	LocalDateTime dateTime;
}
