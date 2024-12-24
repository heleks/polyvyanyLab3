package tech.reliab.course.polyvyanyLab3.bank.service;

import tech.reliab.course.polyvyanyLab3.bank.entity.BankOffice;
import tech.reliab.course.polyvyanyLab3.bank.model.BankOfficeRequest;

import java.util.List;

public interface BankOfficeService {

    BankOffice createBankOffice(BankOfficeRequest bankOfficeRequest);

    void deleteBankAtm(int id);

    BankOffice updateBankOffice(int id, String name);

    BankOffice getBankDtoOfficeById(int id);

    List<BankOffice> getAllBankOffices();
}
