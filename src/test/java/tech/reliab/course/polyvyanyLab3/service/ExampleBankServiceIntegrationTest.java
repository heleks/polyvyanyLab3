package tech.reliab.course.polyvyanyLab3.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.service.BankService;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.util.List;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleBankServiceIntegrationTest {

    @Autowired
    private BankService bankService;

    @Test
    void testCreateBank() {
        Bank createdBank = bankService.createBank("Global Bank");
        Assertions.assertNotNull(createdBank.getId());
        Assertions.assertEquals("Global Bank", createdBank.getName());
    }

    @Test
    void testUpdateBank() {
        Bank createdBank = bankService.createBank("Old Bank");
        Bank updatedBank = bankService.updateBank(createdBank.getId(), "New Global Bank");
        Assertions.assertEquals("New Global Bank", updatedBank.getName());
    }

    @Test
    void testGetBankById() {
        Bank createdBank = bankService.createBank("Premium Bank");
        Bank foundBank = bankService.getBankDtoById(createdBank.getId());
        Assertions.assertEquals("Premium Bank", foundBank.getName());
    }

    @Test
    void testGetAllBanks() {
        bankService.createBank("Bank One");
        bankService.createBank("Bank Two");
        List<Bank> banks = bankService.getAllBanks();
        Assertions.assertEquals(2, banks.size());
    }

    @Test
    void testDeleteBank() {
        Bank createdBank = bankService.createBank("Bank to Remove");
        bankService.deleteBank(createdBank.getId());
        Assertions.assertThrows(
                RuntimeException.class,
                () -> bankService.getBankDtoById(createdBank.getId())
        );
    }
}
