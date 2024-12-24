package tech.reliab.course.polyvyanyLab3.bank.service;

import tech.reliab.course.polyvyanyLab3.bank.entity.BankAtm;
import tech.reliab.course.polyvyanyLab3.bank.model.BankAtmRequest;

import java.util.List;

public interface BankAtmService {
    void requestBankInfo();

    BankAtm createBankAtm(BankAtmRequest bankAtmRequest);

    void deleteBankAtm(int id);

    BankAtm updateBankAtm(int id, String name);

    BankAtm getBankAtmDtoById(int id);

    List<BankAtm> getAllBankAtmsByBankId(int bankId);

    List<BankAtm> getAllBankAtms();
}
