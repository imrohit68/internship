package com.example.internship.Service;

import com.example.internship.Model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EmployeeService {
    String addEmployee(Employee employee);
    List<Employee> getAllEmployee();
    Employee getEmployeeById(String id);
    Page<Employee> getAllEmployeeSorted(int page, int pageSize, String sortField, Sort.Direction sortDirection);
    String deleteEmployee(String id);
    Employee getNthManager(String id,int n);
    Employee upadteEmployee(Employee employee,String id);
}
