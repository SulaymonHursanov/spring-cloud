package ru.semi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.semi.dto.CustomerDto;
import ru.semi.service.CustomerService;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
	public ResponseEntity getCustomerByName (@PathVariable String name) {
		log.info("called endpoint: customer/get/{name} name: {}", name );
		return ResponseEntity.ok(new CustomerDto(name.length(), name));
	}

	@RequestMapping(value = "/get/{name}/manual-trace", method = RequestMethod.GET)
	public ResponseEntity getCustomerUsingSpanManualTracing (@PathVariable String name) {
		CustomerDto customerInfo = customerService.getCustomerInfo(name);
		return ResponseEntity.ok(customerInfo);
	}
}
