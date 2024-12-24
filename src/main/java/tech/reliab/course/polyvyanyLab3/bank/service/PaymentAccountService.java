package tech.reliab.course.polyvyanyLab3.bank.service;

import tech.reliab.course.polyvyanyLab3.bank.entity.PaymentAccount;
import tech.reliab.course.polyvyanyLab3.bank.model.PaymentAccountRequest;

import java.util.List;

public interface PaymentAccountService {
    PaymentAccount createPaymentAccount(PaymentAccountRequest paymentAccountRequest);

    void deletePaymentAccount(int id);

    PaymentAccount updatePaymentAccount(int id, int bankId);

    PaymentAccount getPaymentAccountDtoById(int id);

    List<PaymentAccount> getAllPaymentAccounts();
}
