package tech.reliab.course.polyvyanyLab3.bank.service;

import tech.reliab.course.polyvyanyLab3.bank.entity.Employee;
import tech.reliab.course.polyvyanyLab3.bank.model.EmployeeRequest;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequest employeeRequest);

    void deleteEmployee(int id);

    Employee updateEmployee(int id, String name);

    Employee getEmployeeDtoById(int id);

    List<Employee> getAllEmployees();
}
