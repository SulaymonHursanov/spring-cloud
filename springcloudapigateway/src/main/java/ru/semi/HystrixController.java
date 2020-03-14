package ru.semi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class HystrixController {
	@GetMapping("/users")
	public String firstServiceFallback(){
		return "This is a fallback for user service.";
	}

	@GetMapping("/auth")
	public String authServiceFallback(){
		return "Sorry! Authorization and authentication service is unavailable.";
	}



	@GetMapping("/second")
	public String secondServiceFallback(){
		return "Second Server overloaded! Please try after some time.";
	}
}
