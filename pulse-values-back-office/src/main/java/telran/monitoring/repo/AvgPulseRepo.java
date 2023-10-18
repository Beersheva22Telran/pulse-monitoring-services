package telran.monitoring.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.monitoring.documents.AvgPulseDoc;

public interface AvgPulseRepo extends MongoRepository<AvgPulseDoc, ObjectId> {
List<AvgPulseDoc> findByPatientIdAndDateTimeBetween(long patientId, LocalDateTime from, LocalDateTime to);
}
