package com.sapient.training.service;

import java.util.List;

import com.sapient.training.entity.Employee;
import com.sapient.training.exception.EmployeeException;

public interface EmployeeService {
	public Employee getEmployeeById(Long id) throws EmployeeException;
    public Employee getEmployeeByName(String name) throws EmployeeException;
    public List<Employee> getAllEmployees() throws EmployeeException;
    public boolean exists(String name) throws EmployeeException;
    public Employee save(Employee employee) throws EmployeeException;
    public List<Employee> findByName(String name) throws EmployeeException;
    public List<Employee> findByJobAndSalary(String job,Double salary) throws EmployeeException;	
   	public List<Employee> findByJobAndSalaryGreaterThan(String job, Double salary) throws EmployeeException;
}
