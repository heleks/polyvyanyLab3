package tech.reliab.course.polyvyanyLab3.bank.service.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.polyvyanyLab3.bank.entity.BankOffice;
import tech.reliab.course.polyvyanyLab3.bank.model.BankOfficeRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankOfficeRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.BankOfficeService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleBankOfficeService implements BankOfficeService {
    private final BankOfficeRepository bankOfficeRepository;
    private final BankRepository bankRepository;

    @Override
    public BankOffice createBankOffice(BankOfficeRequest bankOfficeRequest) {
        return bankOfficeRepository.save(
                new BankOffice(
                        bankOfficeRequest.getName(),
                        bankOfficeRequest.getAddress(),
                        bankOfficeRequest.isCanPlaceAtm(),
                        bankOfficeRequest.isCanIssueLoan(),
                        bankOfficeRequest.isCashWithdrawal(),
                        bankOfficeRequest.isCashDeposit(),
                        bankOfficeRequest.getRentCost(),
                        bankRepository.findById(bankOfficeRequest.getBankId()).orElseThrow()
                )
        );
    }

    @Override
    public void deleteBankAtm(int id) {
        bankOfficeRepository.deleteById(id);
    }

    @Override
    public BankOffice updateBankOffice(int id, String name) {
        var bankOffice = bankOfficeRepository.findById(id).orElseThrow();
        bankOffice.setName(name);

        return bankOfficeRepository.save(bankOffice);
    }

    @Override
    public BankOffice getBankDtoOfficeById(int id) {
        return bankOfficeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<BankOffice> getAllBankOffices() {
        return bankOfficeRepository.findAll();
    }
}
