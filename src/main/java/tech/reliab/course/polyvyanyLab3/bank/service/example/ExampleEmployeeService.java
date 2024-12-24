package tech.reliab.course.polyvyanyLab3.bank.service.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.polyvyanyLab3.bank.entity.Employee;
import tech.reliab.course.polyvyanyLab3.bank.model.EmployeeRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankOfficeRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.EmployeeRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.EmployeeService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.save(
                new Employee(
                        employeeRequest.getFullName(),
                        employeeRequest.getBirthDate(),
                        employeeRequest.getPosition(),
                        bankRepository.findById(employeeRequest.getBankId()).orElseThrow(),
                        employeeRequest.isRemoteWork(),
                        bankOfficeRepository.findById(employeeRequest.getBankOfficeId()).orElseThrow(),
                        employeeRequest.isCanIssueLoans(),
                        employeeRequest.getSalary()
                )
        );
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(int id, String name) {
        var employee = employeeRepository.findById(id).orElseThrow();
        employee.setFullName(name);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeDtoById(int id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
