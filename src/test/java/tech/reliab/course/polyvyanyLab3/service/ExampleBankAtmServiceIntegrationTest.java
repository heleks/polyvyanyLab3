package tech.reliab.course.polyvyanyLab3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.entity.BankAtm;
import tech.reliab.course.polyvyanyLab3.bank.entity.BankOffice;
import tech.reliab.course.polyvyanyLab3.bank.entity.Employee;
import tech.reliab.course.polyvyanyLab3.bank.entity.User;
import tech.reliab.course.polyvyanyLab3.bank.model.BankAtmRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankAtmRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankOfficeRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.EmployeeRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.UserRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.example.ExampleBankAtmService;
import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleBankAtmServiceIntegrationTest {

    @Autowired
    private ExampleBankAtmService exampleBankAtmService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankAtmRepository bankAtmRepository;

    @Autowired
    private BankOfficeRepository bankOfficeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        bankAtmRepository.deleteAll();
        bankOfficeRepository.deleteAll();
        employeeRepository.deleteAll();
        userRepository.deleteAll();
        bankRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testRequestBankInfo() {
        List<Bank> banks = bankRepository.findAll();
        assertEquals(6, banks.size());

        Bank testBank = banks.getFirst();
        assertFalse(bankAtmRepository.findAllByBankId(testBank.getId()).isEmpty());
        assertFalse(bankOfficeRepository.findAllByBankId(testBank.getId()).isEmpty());

        exampleBankAtmService.requestBankInfo();
    }

    @Test
    @Order(2)
    void testInitializeBankInfo() {
        String bankName = "Тест";
        exampleBankAtmService.createBankAtm(new BankAtmRequest(bankName, "bimbim", 1, 1, 1, true, true, 1));

        Bank bank = bankRepository.findByName(bankName).orElseThrow(() -> new AssertionError("Банк не найден"));
        assertEquals(bankName, bank.getName());

        List<BankAtm> bankAtms = bankAtmRepository.findAllByBankId(bank.getId());
        List<BankOffice> bankOffices = bankOfficeRepository.findAllByBankId(bank.getId());
        List<Employee> employees = employeeRepository.findAllByBankId(bank.getId());
        List<User> users = userRepository.findAllByBanksId(bank.getId());

        assertEquals(6, bankAtms.size());
        assertEquals(6, bankOffices.size());
        assertEquals(6, employees.size());
        assertTrue(users.size() >= 6);
    }
}
