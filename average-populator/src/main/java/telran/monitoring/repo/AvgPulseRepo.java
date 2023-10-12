package telran.monitoring.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.monitoring.documents.AvgPulseDoc;

public interface AvgPulseRepo extends MongoRepository<AvgPulseDoc, ObjectId> {

}
