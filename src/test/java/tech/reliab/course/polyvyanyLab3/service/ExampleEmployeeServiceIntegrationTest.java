package tech.reliab.course.polyvyanyLab3.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.entity.BankOffice;
import tech.reliab.course.polyvyanyLab3.bank.entity.Employee;
import tech.reliab.course.polyvyanyLab3.bank.model.EmployeeRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankOfficeRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.EmployeeService;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleEmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankOfficeRepository bankOfficeRepository;

    private Bank testBank;
    private BankOffice testBankOffice;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Global Bank"));
        testBankOffice = bankOfficeRepository.save(new BankOffice("Downtown Office", "Main Street, 12", testBank, true, 1200, true, true));
    }

    @Test
    void testCreateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("James Brown");
        request.setBirthDate(LocalDate.of(1985, 2, 25));
        request.setPosition("Supervisor");
        request.setBankId(testBank.getId());
        request.setRemoteWork(false);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(true);
        request.setSalary(4500);

        Employee createdEmployee = employeeService.createEmployee(request);

        Assertions.assertNotNull(createdEmployee.getId());
        Assertions.assertEquals("James Brown", createdEmployee.getFullName());
        Assertions.assertEquals("Supervisor", createdEmployee.getPosition());
        Assertions.assertEquals(testBank.getId(), createdEmployee.getBank().getId());
        Assertions.assertEquals(testBankOffice.getId(), createdEmployee.getBankOffice().getId());
        Assertions.assertTrue(createdEmployee.isCanIssueLoans());
        Assertions.assertEquals(4500, createdEmployee.getSalary());
    }

    @Test
    void testUpdateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("Alice White");
        request.setBirthDate(LocalDate.of(1990, 3, 15));
        request.setPosition("Manager");
        request.setBankId(testBank.getId());
        request.setRemoteWork(true);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(false);
        request.setSalary(3800);

        Employee createdEmployee = employeeService.createEmployee(request);
        Employee updatedEmployee = employeeService.updateEmployee(createdEmployee.getId(), "Alice Green");

        Assertions.assertEquals("Alice Green", updatedEmployee.getFullName());
    }

    @Test
    void testGetEmployeeById() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("Carlos Red");
        request.setBirthDate(LocalDate.of(1992, 7, 9));
        request.setPosition("Teller");
        request.setBankId(testBank.getId());
        request.setRemoteWork(false);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(false);
        request.setSalary(2200);

        Employee createdEmployee = employeeService.createEmployee(request);
        Employee foundEmployee = employeeService.getEmployeeDtoById(createdEmployee.getId());
        Assertions.assertNotNull(foundEmployee);
        Assertions.assertEquals("Carlos Red", foundEmployee.getFullName());
    }

    @Test
    void testGetAllEmployees() {
        EmployeeRequest request1 = new EmployeeRequest();
        request1.setFullName("Eva Black");
        request1.setBirthDate(LocalDate.of(1993, 11, 5));
        request1.setPosition("Clerk");
        request1.setBankId(testBank.getId());
        request1.setRemoteWork(true);
        request1.setBankOfficeId(testBankOffice.getId());
        request1.setCanIssueLoans(false);
        request1.setSalary(2800);
        employeeService.createEmployee(request1);

        EmployeeRequest request2 = new EmployeeRequest();
        request2.setFullName("Frank Blue");
        request2.setBirthDate(LocalDate.of(1987, 9, 17));
        request2.setPosition("Cashier");
        request2.setBankId(testBank.getId());
        request2.setRemoteWork(false);
        request2.setBankOfficeId(testBankOffice.getId());
        request2.setCanIssueLoans(true);
        request2.setSalary(3200);
        employeeService.createEmployee(request2);

        List<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertEquals(2, employees.size());
    }

    @Test
    void testDeleteEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("Grace Yellow");
        request.setBirthDate(LocalDate.of(1994, 4, 18));
        request.setPosition("Administrator");
        request.setBankId(testBank.getId());
        request.setRemoteWork(false);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(true);
        request.setSalary(2600);

        Employee createdEmployee = employeeService.createEmployee(request);

        employeeService.deleteEmployee(createdEmployee.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeDtoById(createdEmployee.getId())
        );
    }
}
