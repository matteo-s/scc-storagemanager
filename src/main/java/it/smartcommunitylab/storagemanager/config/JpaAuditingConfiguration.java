package it.smartcommunitylab.storagemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import it.smartcommunitylab.storagemanager.audit.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
}
