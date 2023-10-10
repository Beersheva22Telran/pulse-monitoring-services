package telran.monitoring.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RedisHash
@Getter
@AllArgsConstructor
public class LastPulseValue {
	@Id
long patientId;
	int value;
	

}
