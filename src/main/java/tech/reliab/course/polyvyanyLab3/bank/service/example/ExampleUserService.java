package tech.reliab.course.polyvyanyLab3.bank.service.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.polyvyanyLab3.bank.entity.User;
import tech.reliab.course.polyvyanyLab3.bank.model.UserRequest;
import tech.reliab.course.polyvyanyLab3.bank.repository.CreditAccountRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.PaymentAccountRepository;
import tech.reliab.course.polyvyanyLab3.bank.repository.UserRepository;
import tech.reliab.course.polyvyanyLab3.bank.service.UserService;

import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExampleUserService implements UserService {
    private final UserRepository userRepository;
    private final CreditAccountRepository creditAccountRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public void requestUserInfo() {
        var users = userRepository.findAll();
        for (var user : users) {
            log.info(user.toString());
        }


        var scanner = new Scanner(System.in);

        log.info("Введите ID пользователя");
        var id = scanner.nextInt();

        var user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.warn("Пользователь не найден");
            return;
        }

        var creditAccounts = creditAccountRepository.findByUserId(user.get().getId());
        var paymentAccounts = paymentAccountRepository.findAllByUserId(user.get().getId());

        log.info("[[ Информация о пользователе ]]");
        log.info(user.get().toString());
        log.info(creditAccounts.toString());
        log.info(paymentAccounts.toString());
    }

    @Override
    public User createUser(UserRequest userRequest) {
        return userRepository.save(
                new User(
                        userRequest.getFullName(),
                        userRequest.getBirthDate(),
                        userRequest.getJob()
                )
        );
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(int id, String name) {
        var user = userRepository.findById(id).orElseThrow();
        user.setFullName(name);

        return userRepository.save(user);
    }

    @Override
    public User getUserDtoById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
