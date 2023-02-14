package com.infybuzz.service;

import com.infybuzz.feignclients.AddressFeignClient;
import com.infybuzz.feignclients.NewAddressFeignClient;
import com.infybuzz.response.AddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infybuzz.entity.Student;
import com.infybuzz.repository.StudentRepository;
import com.infybuzz.request.CreateStudentRequest;
import com.infybuzz.response.StudentResponse;

@Service
public class StudentService {

	Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AddressFeignClient addressFeignClient;

	@Autowired
	NewAddressFeignClient newAddressFeignClient;

	@Autowired
	CommonService commonService;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {
		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);
		StudentResponse studentResponse = new StudentResponse(student);
		studentResponse.setAddressResponse(commonService.getAddressById(createStudentRequest.getAddressId()));
		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		logger.info("Hello");
		Student student = studentRepository.findById(id).get();
		StudentResponse studentResponse = new StudentResponse(student);
		AddressResponse addressResponse = commonService.getAddressById(student.getAddressId());
		studentResponse.setAddressResponse(addressResponse);
		return studentResponse;
	}

	/*@CircuitBreaker(name = "addressService", fallbackMethod = "fallbackGetAddressId")
	public AddressResponse getAddressById(long addressId) {
		return newAddressFeignClient.findById(addressId);
	}

	public AddressResponse fallbackGetAddressId(long addressId, Throwable throwable) {
		return new AddressResponse();
	}*/

}
