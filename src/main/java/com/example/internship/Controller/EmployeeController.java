package com.example.internship.Controller;

import com.example.internship.Model.Employee;
import com.example.internship.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee){
        String id = employeeService.addEmployee(employee);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }
    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employees = employeeService.getAllEmployee();
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }
    @GetMapping("/getById/{Id}")
    public ResponseEntity<Employee> getById(@PathVariable String Id){
        Employee employee = employeeService.getEmployeeById(Id);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }
    @PutMapping("/update/{Id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String Id,@RequestBody Employee employeeUpdated){
        Employee employee = employeeService.upadteEmployee(employeeUpdated,Id);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }
    @DeleteMapping("delete/{Id}")
    public ResponseEntity<String> delete(@PathVariable String Id){

        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
    @GetMapping("getNthLevelManager/{Id}/{level}")
    public ResponseEntity<Employee> nthLevelManager(@PathVariable String Id,@PathVariable Integer level){
        Employee employee = employeeService.getNthManager(Id,level);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @GetMapping("/getAllSorted")
    public Page<Employee> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "2") int sizePerPage,
                                        @RequestParam(defaultValue = "EmployeeName")  String field,
                                        @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection) {
       Page<Employee> employees = employeeService.getAllEmployeeSorted(page,sizePerPage,field,sortDirection);
       return employees;
    }
}
