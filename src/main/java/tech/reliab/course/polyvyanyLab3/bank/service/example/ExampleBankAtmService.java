package tech.reliab.course.polyvyanyLab3.bank.service.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.polyvyanyLab3.bank.entity.*;
import tech.reliab.course.polyvyanyLab3.bank.model.BankAtmRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.*;
import tech.reliab.course.polyvyanyLab3.bank.service.BankAtmService;

import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleBankAtmService implements BankAtmService {
    private static final int BANKS_COUNT = 5;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;
    private final EmployeeRepository employeeRepository;
    private final BankAtmRepository bankAtmRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final CreditAccountRepository creditAccountRepository;

    @Override
    public void requestBankInfo() {
        var banks = bankRepository.findAll();
        for (var bank : banks) {
            log.info(bank.toString());
        }

        var scanner = new Scanner(System.in);
        log.info("Введите ID банка");
        int id = scanner.nextInt();
        var bank = bankRepository.findById(id);
        if (bank.isEmpty()) {
            log.warn("Банк не найден");
            return;
        }

        log.info("[[ Информация о банке ]]");
        log.info(bank.get().toString());
        log.info(bankAtmRepository.findAllByBankId(bank.get().getId()).toString());
        log.info(bankOfficeRepository.findAllByBankId(bank.get().getId()).toString());
        log.info(employeeRepository.findAllByBankId(bank.get().getId()).toString());
        log.info(userRepository.findAllByBanksId(bank.get().getId()).toString());
    }

    @Override
    public BankAtm createBankAtm(BankAtmRequest bankAtmRequest) {
        return bankAtmRepository.save(
                new BankAtm(
                        bankAtmRequest.getName(),
                        bankAtmRequest.getAddress(),
                        bankRepository.findById(bankAtmRequest.getBankId()).orElseThrow(),
                        bankOfficeRepository.findById(bankAtmRequest.getLocationId()).orElseThrow(),
                        employeeRepository.findById(bankAtmRequest.getEmployeeId()).orElseThrow(),
                        bankAtmRequest.isCashWithdrawal(),
                        bankAtmRequest.isCashDeposit(),
                        bankAtmRequest.getMaintenanceCost()

            )
        );
    }

    @Override
    public void deleteBankAtm(int id) {
        bankAtmRepository.deleteById(id);
    }

    @Override
    public BankAtm updateBankAtm(int id, String name) {
        var bankAtm = bankAtmRepository.findById(id).orElseThrow();
        bankAtm.setName(name);

        return bankAtmRepository.save(bankAtm);
    }

    @Override
    public BankAtm getBankAtmDtoById(int id) {
        return bankAtmRepository.findById(id).orElseThrow();
    }

    @Override
    public List<BankAtm> getAllBankAtmsByBankId(int bankId) {
        return bankAtmRepository.findAllByBankId(bankId);
    }

    @Override
    public List<BankAtm> getAllBankAtms() {
        return bankAtmRepository.findAll();
    }
}
