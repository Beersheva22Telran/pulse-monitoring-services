package telran.monitoring.repo;

import org.springframework.data.repository.CrudRepository;

import telran.monitoring.entity.LastPulseValue;

public interface LastPulseValueRepo extends CrudRepository<LastPulseValue, Long>{

}
