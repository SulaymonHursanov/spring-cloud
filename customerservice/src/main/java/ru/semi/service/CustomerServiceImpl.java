package ru.semi.service;

import brave.Span;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.semi.dto.CustomerDto;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private Tracer tracer;

	@Override
	public CustomerDto getCustomerInfo(String name) {
		log.info("Starting");
		Span newSpan = tracer.nextSpan().name("new sleuth span").start();
		try {
			Tracer.SpanInScope span = tracer.withSpanInScope(newSpan.start());
			log.info("Check (new span)");
		} finally {
			newSpan.finish();
		}
		log.info("Finish");
		return new CustomerDto(name.length(), name);
	}
}
