package ru.semi.service;

import ru.semi.dto.CustomerDto;

public interface CustomerService {
	CustomerDto getCustomerInfo(String name);
}
