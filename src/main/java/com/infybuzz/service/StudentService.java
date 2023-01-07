package com.infybuzz.service;

import com.infybuzz.feignclients.AddressFeignClient;
import com.infybuzz.feignclients.NewAddressFeignClient;
import com.infybuzz.response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infybuzz.entity.Student;
import com.infybuzz.repository.StudentRepository;
import com.infybuzz.request.CreateStudentRequest;
import com.infybuzz.response.StudentResponse;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AddressFeignClient addressFeignClient;

	@Autowired
	NewAddressFeignClient newAddressFeignClient;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {
		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);
		StudentResponse studentResponse = new StudentResponse(student);
		studentResponse.setAddressResponse(newAddressFeignClient.findById(createStudentRequest.getAddressId()));
		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		Student student = studentRepository.findById(id).get();
		StudentResponse studentResponse = new StudentResponse(student);
		AddressResponse addressResponse = newAddressFeignClient.findById(student.getAddressId());
		studentResponse.setAddressResponse(addressResponse);
		return studentResponse;
	}
}
