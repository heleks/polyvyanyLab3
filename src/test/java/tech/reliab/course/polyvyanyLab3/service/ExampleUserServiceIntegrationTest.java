package tech.reliab.course.polyvyanyLab3.service; 

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.polyvyanyLab3.bank.polyvyanyLab3Application;
import tech.reliab.course.polyvyanyLab3.bank.entity.User;
import tech.reliab.course.polyvyanyLab3.bank.model.UserRequest;
import tech.reliab.course.polyvyanyLab3.bank.service.UserService;
import tech.reliab.course.polyvyanyLab3.container.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = polyvyanyLab3Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleUserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setFullName("Alice Wonderland");
        request.setBirthDate(LocalDate.of(1985, 2, 15));
        request.setJob("Engineer");

        User createdUser = userService.createUser(request);
        Assertions.assertNotNull(createdUser.getId());
        Assertions.assertEquals("Alice Wonderland", createdUser.getFullName());
        Assertions.assertEquals("Engineer", createdUser.getJob());
    }

    @Test
    void testUpdateUser() {
        UserRequest request = new UserRequest();
        request.setFullName("Bob Builder");
        request.setBirthDate(LocalDate.of(1990, 8, 20));
        request.setJob("Constructor");

        User user = userService.createUser(request);
        User updatedUser = userService.updateUser(user.getId(), "Bob the Builder");
        Assertions.assertEquals("Bob the Builder", updatedUser.getFullName());
    }

    @Test
    void testGetUserById() {
        UserRequest request = new UserRequest();
        request.setFullName("Charlie Brown");
        request.setBirthDate(LocalDate.of(1978, 11, 12));
        request.setJob("Artist");

        User user = userService.createUser(request);
        User foundUser = userService.getUserDtoById(user.getId());
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("Charlie Brown", foundUser.getFullName());
    }

    @Test
    void testGetAllUsers() {
        UserRequest request1 = new UserRequest();
        request1.setFullName("David Green");
        request1.setBirthDate(LocalDate.of(1995, 9, 1));
        request1.setJob("Developer");
        userService.createUser(request1);

        UserRequest request2 = new UserRequest();
        request2.setFullName("Emma Blue");
        request2.setBirthDate(LocalDate.of(1992, 5, 13));
        request2.setJob("Designer");
        userService.createUser(request2);

        List<User> users = userService.getAllUsers();
        Assertions.assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser() {
        UserRequest request = new UserRequest();
        request.setFullName("Fiona Green");
        request.setBirthDate(LocalDate.of(1994, 7, 7));
        request.setJob("Manager");

        User user = userService.createUser(request);
        userService.deleteUser(user.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.getUserDtoById(user.getId())
        );
    }
}
