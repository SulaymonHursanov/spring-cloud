package ru.semi.config;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public Sampler defaultSampler () {
		return Sampler.ALWAYS_SAMPLE;
	}


}
