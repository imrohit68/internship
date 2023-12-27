package com.example.internship.Service.ServiceImpl;

import com.example.internship.Exceptions.EmployeeAlreadyExists;
import com.example.internship.Exceptions.EmployeeDoesNotExist;
import com.example.internship.Exceptions.NullFieldException;
import com.example.internship.Model.Employee;
import com.example.internship.Repository.EmployeeRepo;
import com.example.internship.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final JavaMailSender javaMailSender;
    @Override
    public String addEmployee(Employee employee) {
        employee.setId(UUID.randomUUID().toString());
        if(employee.getEmployeeName()==null||employee.getPhoneNumber()==null||employee.getEmail()==null){
            throw new NullFieldException("Fields Cannot Be Null ... Please Enter Values In Each Field");
        }
        if(employee.getEmployeeName().isEmpty()||employee.getPhoneNumber().isEmpty()||employee.getEmail().isEmpty()){
            throw new NullFieldException("Fields Cannot Be Empty ... Please Enter Values In Each Field");
        }
        if(!employeeRepo.existsByEmail(employee.getEmail())&&!employeeRepo.existsByPhoneNumber(employee.getPhoneNumber())){
            employeeRepo.save(employee);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("internshipsample32@gmail.com");
            simpleMailMessage.setSubject("New Employee Added Under You");
            simpleMailMessage.setText(String.format("Employee with name : %s , Phone Number ; %s and Email : %s has been added under you",employee.getEmployeeName(),employee.getPhoneNumber(),employee.getEmail()));
            Employee employee1 = employeeRepo.findById(employee.getReportsTo())
                    .orElseThrow(()-> new EmployeeDoesNotExist("Employee Does not Exists with id: "+employee.getReportsTo()));
            simpleMailMessage.setTo(employee1.getEmail());
            javaMailSender.send(simpleMailMessage);
        }else{
            throw new EmployeeAlreadyExists("Employee Already Exists with Email or PhoneNumber");
        }
        return employee.getId();
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employees = employeeRepo.findAll();
        return employees;
    }

    @Override
    public Employee getEmployeeById(String id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(()-> new EmployeeDoesNotExist("Employee Does not Exists with id: "+id));
        return employee;
    }

    @Override
    public Page<Employee> getAllEmployeeSorted(int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(page,pageSize, sortDirection,sortField);
        return employeeRepo.findAll(pageable);
    }

    @Override
    public String deleteEmployee(String id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(()-> new EmployeeDoesNotExist("Employee Does not Exists with id: "+id));
        employeeRepo.delete(employee);
        return "Deleted Successfully";
    }

    @Override
    public Employee getNthManager(String id, int n) {
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new EmployeeDoesNotExist("Employee Does not Exists with id: "+id));
        for (int i = 1;i<=n;i++){
            Employee employee1 = employeeRepo.findById(employee.getReportsTo()).orElseThrow(()-> new EmployeeDoesNotExist("Employee Does not Exists with id"+id));
            employee = employee1;
        }
        return employee;
    }

    @Override
    public Employee upadteEmployee(Employee employeeUpdated, String id) {
        if(employeeUpdated.getEmployeeName()==null||employeeUpdated.getPhoneNumber()==null||employeeUpdated.getEmail()==null||employeeUpdated.getReportsTo()==null){
            throw new NullFieldException("Fields Cannot Be Null ... Please Enter Values In Each Field");
        }
        if(employeeUpdated.getEmployeeName().isEmpty()||employeeUpdated.getPhoneNumber().isEmpty()||employeeUpdated.getEmail().isEmpty()||employeeUpdated.getReportsTo().isEmpty()){
            throw new NullFieldException("Fields Cannot Be Empty ... Please Enter Values In Each Field");
        }
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(()-> new EmployeeDoesNotExist("Employee Does not Exists with id: "+id));
        if(employee.getEmail().equals(employeeUpdated.getEmail())&&employee.getPhoneNumber().equals(employeeUpdated.getPhoneNumber())){
            employee.setEmployeeName(employeeUpdated.getEmployeeName());
            employee.setReportsTo(employeeUpdated.getReportsTo());
            employee.setProfileImage(employeeUpdated.getProfileImage());
            Employee updated = employeeRepo.save(employee);
            return updated;
        }
        else{
            if(!employeeRepo.existsByEmail(employeeUpdated.getEmail())&&!employeeRepo.existsByPhoneNumber(employeeUpdated.getPhoneNumber())){
                employee.setEmployeeName(employeeUpdated.getEmployeeName());
                employee.setReportsTo(employeeUpdated.getReportsTo());
                employee.setProfileImage(employeeUpdated.getProfileImage());
                employee.setEmail(employeeUpdated.getEmail());
                employee.setPhoneNumber(employeeUpdated.getPhoneNumber());
                Employee updated = employeeRepo.save(employee);
                return updated;
            }else{
                throw new EmployeeAlreadyExists("Employee Already Exists with Email or PhoneNumber");
            }
        }
    }
}
