package tech.reliab.course.polyvyanyLab3.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.polyvyanyLab3.bank.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

    void deleteById(int id);

    List<User> findAllByBanksId(int bankId);
}
