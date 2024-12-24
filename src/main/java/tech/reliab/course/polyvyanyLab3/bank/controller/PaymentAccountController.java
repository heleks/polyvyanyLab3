package tech.reliab.course.polyvyanyLab3.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.polyvyanyLab3.bank.entity.PaymentAccount;
import tech.reliab.course.polyvyanyLab3.bank.model.PaymentAccountRequest;

import java.util.List;

public interface PaymentAccountController {

    ResponseEntity<PaymentAccount> createPaymentAccount(@RequestBody PaymentAccountRequest paymentAccountRequest);

    ResponseEntity<Void> deletePaymentAccount(@PathVariable int id);

    ResponseEntity<PaymentAccount> updatePaymentAccount(@PathVariable int id, @RequestParam(name = "bankId") int bankId);

    ResponseEntity<PaymentAccount> getBankByPaymentAccount(@PathVariable int id);

    ResponseEntity<List<PaymentAccount>> getAllPaymentAccounts();
}
