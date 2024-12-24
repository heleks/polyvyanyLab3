package tech.reliab.course.polyvyanyLab3.bank.service;

import org.springframework.http.ResponseEntity;
import tech.reliab.course.polyvyanyLab3.bank.entity.Bank;

import java.util.List;

public interface BankService {
    Bank createBank(String bankName);

    void deleteBank(int id);

    Bank updateBank(int id, String bankName);

    List<Bank> getAllBanks();

    Bank getBankDtoById(int id);
}
