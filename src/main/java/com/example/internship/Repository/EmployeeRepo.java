package com.example.internship.Repository;

import com.example.internship.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepo extends MongoRepository<Employee,String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
