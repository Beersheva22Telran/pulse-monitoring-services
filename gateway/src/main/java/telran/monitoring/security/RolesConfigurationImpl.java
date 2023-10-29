package telran.monitoring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import telran.spring.security.RolesConfiguration;
@Configuration
public class RolesConfigurationImpl implements RolesConfiguration {

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(requests -> requests
				.anyRequest().authenticated());

	}

}
