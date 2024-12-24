package tech.reliab.course.polyvyanyLab3.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.entity.PaymentAccount;
import tech.reliab.course.polyvyanyLab3.bank.entity.User;
import tech.reliab.course.polyvyanyLab3.bank.model.PaymentAccountRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.UserRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.PaymentAccountService;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExamplePaymentAccountServiceIntegrationTest {

    @Autowired
    private PaymentAccountService paymentAccountService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private UserRepository userRepository;

    private Bank testBank;
    private User testUser;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Global Bank"));
        testUser = userRepository.save(new User("Alice", "Smith", LocalDate.of(1995, 3, 10), testBank.getId()));
    }

    @Test
    void testCreatePaymentAccount() {
        PaymentAccountRequest request = new PaymentAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());

        PaymentAccount createdAccount = paymentAccountService.createPaymentAccount(request);
        Assertions.assertNotNull(createdAccount.getId());
        Assertions.assertEquals(testUser.getId(), createdAccount.getUser().getId());
        Assertions.assertEquals(testBank.getId(), createdAccount.getBank().getId());
    }

    @Test
    void testUpdatePaymentAccount() {
        PaymentAccountRequest request = new PaymentAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());

        PaymentAccount account = paymentAccountService.createPaymentAccount(request);

        Bank newBank = bankRepository.save(new Bank("New Global Bank"));

        PaymentAccount updatedAccount = paymentAccountService.updatePaymentAccount(account.getId(), newBank.getId());
        Assertions.assertEquals(newBank.getId(), updatedAccount.getBank().getId());
    }

    @Test
    void testGetPaymentAccountById() {
        PaymentAccountRequest request = new PaymentAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());

        PaymentAccount account = paymentAccountService.createPaymentAccount(request);
        PaymentAccount found = paymentAccountService.getPaymentAccountDtoById(account.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals(testBank.getId(), found.getBank().getId());
    }

    @Test
    void testGetAllPaymentAccounts() {
        PaymentAccountRequest request1 = new PaymentAccountRequest();
        request1.setUserId(testUser.getId());
        request1.setBankId(testBank.getId());
        paymentAccountService.createPaymentAccount(request1);

        PaymentAccountRequest request2 = new PaymentAccountRequest();
        request2.setUserId(testUser.getId());
        request2.setBankId(testBank.getId());
        paymentAccountService.createPaymentAccount(request2);

        List<PaymentAccount> accounts = paymentAccountService.getAllPaymentAccounts();
        Assertions.assertEquals(2, accounts.size());
    }

    @Test
    void testDeletePaymentAccount() {
        PaymentAccountRequest request = new PaymentAccountRequest();
        request.setUserId(testUser.getId());
        request.setBankId(testBank.getId());

        PaymentAccount account = paymentAccountService.createPaymentAccount(request);
        paymentAccountService.deletePaymentAccount(account.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> paymentAccountService.getPaymentAccountDtoById(account.getId())
        );
    }
}
