package tech.reliab.course.polyvyanyLab3.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.entity.CreditAccount;
import tech.reliab.course.polyvyanyLab3.bank.entity.Employee;
import tech.reliab.course.polyvyanyLab3.bank.entity.PaymentAccount;
import tech.reliab.course.polyvyanyLab3.bank.entity.User;
import tech.reliab.course.polyvyanyLab3.bank.model.CreditAccountRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.EmployeeRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.PaymentAccountRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.UserRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.CreditAccountService;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleCreditAccountServiceIntegrationTest {

    @Autowired
    private CreditAccountService creditAccountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    private User testUser;
    private Bank testBank;
    private Employee testEmployee;
    private PaymentAccount testPaymentAccount;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Global Bank"));
        testUser = userRepository.save(new User("Jane", "Doe", LocalDate.of(1990, 3, 10), testBank.getId()));
        testEmployee = employeeRepository.save(new Employee(testBank, "John", "Doe", "Loan Officer", true, true, 5000));
        testPaymentAccount = paymentAccountRepository.save(new PaymentAccount(testUser, 15000));
    }

    @Test
    void testCreateCreditAccount() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2022, 1, 1));
        request.setLoanTermMonths(12);
        request.setInterestRate(6.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountService.createCreditAccount(request);
        Assertions.assertNotNull(createdAccount.getId());
        Assertions.assertEquals(testUser.getId(), createdAccount.getUser().getId());
        Assertions.assertEquals(testBank.getId(), createdAccount.getBank().getId());
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), createdAccount.getStartDate());
        Assertions.assertEquals(12, createdAccount.getLoanTermMonths());
        Assertions.assertEquals(6.0, createdAccount.getInterestRate());
        Assertions.assertEquals(testEmployee.getId(), createdAccount.getEmployee().getId());
        Assertions.assertEquals(testPaymentAccount.getId(), createdAccount.getPaymentAccount().getId());
    }

    @Test
    void testUpdateCreditAccount() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2022, 5, 1));
        request.setLoanTermMonths(24);
        request.setInterestRate(7.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountService.createCreditAccount(request);

        Bank newBank = bankRepository.save(new Bank("New Bank"));
        CreditAccount updatedAccount = creditAccountService.updateCreditAccount(createdAccount.getId(), newBank.getId());
        Assertions.assertEquals(newBank.getId(), updatedAccount.getBank().getId());
    }

    @Test
    void testGetCreditAccountById() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2023, 7, 10));
        request.setLoanTermMonths(36);
        request.setInterestRate(5.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountService.createCreditAccount(request);

        CreditAccount foundAccount = creditAccountService.getCreditAccountDtoById(createdAccount.getId());
        Assertions.assertNotNull(foundAccount);
        Assertions.assertEquals(5.0, foundAccount.getInterestRate());
    }

    @Test
    void testGetAllCreditAccounts() {
        CreditAccountRequest request1 = new CreditAccountRequest();
        request1.setUserId(testUser.getId());
        request1.setBankId(testBank.getId());
        request1.setStartDate(LocalDate.of(2021, 8, 5));
        request1.setLoanTermMonths(6);
        request1.setInterestRate(6.5);
        request1.setEmployeeId(testEmployee.getId());
        request1.setPaymentAccountId(testPaymentAccount.getId());
        creditAccountService.createCreditAccount(request1);

        CreditAccountRequest request2 = new CreditAccountRequest();
        request2.setUserId(testUser.getId());
        request2.setBankId(testBank.getId());
        request2.setStartDate(LocalDate.of(2021, 9, 6));
        request2.setLoanTermMonths(18);
        request2.setInterestRate(7.2);
        request2.setEmployeeId(testEmployee.getId());
        request2.setPaymentAccountId(testPaymentAccount.getId());
        creditAccountService.createCreditAccount(request2);

        List<CreditAccount> accounts = creditAccountService.getAllCreditAccounts();
        Assertions.assertEquals(2, accounts.size());
    }

    @Test
    void testDeleteCreditAccount() {
        CreditAccountRequest request = new CreditAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());
        request.setStartDate(LocalDate.of(2023, 10, 1));
        request.setLoanTermMonths(12);
        request.setInterestRate(5.0);
        request.setEmployeeId(testEmployee.getId());
        request.setPaymentAccountId(testPaymentAccount.getId());

        CreditAccount createdAccount = creditAccountService.createCreditAccount(request);
        creditAccountService.deleteCreditAccount(createdAccount.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> creditAccountService.getCreditAccountDtoById(createdAccount.getId())
        );
    }
}
