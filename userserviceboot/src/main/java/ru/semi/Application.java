package ru.semi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableHystrix
@RestController
@RequestMapping(value = "/users")
@Configuration
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@HystrixCommand(fallbackMethod = "getAllUsersFallBack")
	@GetMapping(value = "/all")
	public ResponseEntity getAllUsers () {
		return ResponseEntity.ok(Arrays.asList("TestUser", "Admin"));
	}

	public ResponseEntity getAllUsersFallBack () {
		return ResponseEntity.ok(Collections.singletonList("FallBackUser"));
	}

	@GetMapping(value = "/exception")
	public ResponseEntity throwException() {
		throw new IllegalArgumentException("Just exception");
	}

	@GetMapping(value = "/timeout")
	public ResponseEntity timeout(@RequestParam(required = false, defaultValue = "3") int seconds) throws InterruptedException {
		Thread.sleep(seconds*1000);
		return ResponseEntity.ok().build();
	}


	@Bean
	public SpringRequestFilter logFilter() {
		SpringRequestFilter filter = new SpringRequestFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(1000000);
		filter.setAfterMessagePrefix("");
		filter.setAfterMessageSuffix("");
		filter.setIncludeHeaders(false);
		return filter;
	}

}
