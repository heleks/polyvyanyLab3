package tech.reliab.course.polyvyanyLab3.bank.service.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;
import tech.reliab.course.polyvyanyLab3.bank.repository.BankRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.BankService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleBankService implements BankService {
    private final BankRepository bankRepository;

    @Override
    public Bank createBank(String bankName) {
        return bankRepository.save(new Bank(bankName));
    }

    @Override
    public void deleteBank(int id) {
        bankRepository.deleteById(id);
    }

    @Override
    public Bank updateBank(int id, String bankName) {
        var bank = bankRepository.findById(id).orElseThrow();
        bank.setName(bankName);

        return bankRepository.save(bank);
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Bank getBankDtoById(int id) {
        return bankRepository.findById(id).orElseThrow();
    }
}
