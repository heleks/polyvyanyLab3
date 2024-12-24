package tech.reliab.course.polyvyanyLab3.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.entity.BankOffice;
import tech.reliab.course.polyvyanyLab3.bank.model.BankOfficeRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.BankOfficeService;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleBankOfficeServiceIntegrationTest {

    @Autowired
    private BankOfficeService bankOfficeService;

    @Autowired
    private BankRepository bankRepository;

    private Bank testBank;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Sample Bank", "Sample Bank Address", 60000, 60, 25, 6));
    }

    @Test
    void testCreateBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Head Office");
        request.setAddress("Headquarter Avenue 5");
        request.setCanPlaceAtm(true);
        request.setCanIssueLoan(true);
        request.setCashWithdrawal(true);
        request.setCashDeposit(false);
        request.setRentCost(1200);
        request.setBankId(testBank.getId());

        BankOffice createdOffice = bankOfficeService.createBankOffice(request);
        Assertions.assertNotNull(createdOffice.getId());
        Assertions.assertEquals("Head Office", createdOffice.getName());
        Assertions.assertEquals("Headquarter Avenue 5", createdOffice.getAddress());
        Assertions.assertTrue(createdOffice.isCanPlaceAtm());
        Assertions.assertTrue(createdOffice.isCanIssueLoan());
        Assertions.assertTrue(createdOffice.isCashWithdrawal());
        Assertions.assertFalse(createdOffice.isCashDeposit());
        Assertions.assertEquals(new BigDecimal("1200"), createdOffice.getRentCost());
        Assertions.assertEquals(testBank.getId(), createdOffice.getBank().getId());
    }

    @Test
    void testUpdateBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Branch Office");
        request.setAddress("Branch Street 15");
        request.setCanPlaceAtm(false);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(false);
        request.setCashDeposit(true);
        request.setRentCost(700);
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeService.createBankOffice(request);
        BankOffice updatedOffice = bankOfficeService.updateBankOffice(office.getId(), "New Branch Office");
        Assertions.assertEquals("New Branch Office", updatedOffice.getName());
    }

    @Test
    void testGetBankOfficeById() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Customer Service Office");
        request.setAddress("Customer Street 3");
        request.setCanPlaceAtm(true);
        request.setCanIssueLoan(true);
        request.setCashWithdrawal(true);
        request.setCashDeposit(false);
        request.setRentCost(1500);
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeService.createBankOffice(request);
        BankOffice found = bankOfficeService.getBankDtoOfficeById(office.getId());
        Assertions.assertNotNull(found, "Офис должен быть найден по ID");
        Assertions.assertEquals("Customer Service Office", found.getName());
    }

    @Test
    void testGetAllBankOffices() {
        BankOfficeRequest request1 = new BankOfficeRequest();
        request1.setName("Office X");
        request1.setAddress("Street X");
        request1.setCanPlaceAtm(true);
        request1.setCanIssueLoan(true);
        request1.setCashWithdrawal(true);
        request1.setCashDeposit(true);
        request1.setRentCost(1800);
        request1.setBankId(testBank.getId());
        bankOfficeService.createBankOffice(request1);

        BankOfficeRequest request2 = new BankOfficeRequest();
        request2.setName("Office Y");
        request2.setAddress("Street Y");
        request2.setCanPlaceAtm(false);
        request2.setCanIssueLoan(false);
        request2.setCashWithdrawal(false);
        request2.setCashDeposit(false);
        request2.setRentCost(3500);
        request2.setBankId(testBank.getId());
        bankOfficeService.createBankOffice(request2);

        List<BankOffice> offices = bankOfficeService.getAllBankOffices();
        Assertions.assertEquals(2, offices.size());
    }

    @Test
    void testDeleteBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Temporary Office");
        request.setAddress("Temporary Street 5");
        request.setCanPlaceAtm(false);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(false);
        request.setCashDeposit(false);
        request.setRentCost(800);
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeService.createBankOffice(request);
        bankOfficeService.deleteBankAtm(office.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> bankOfficeService.getBankDtoOfficeById(office.getId())
        );
    }
}
