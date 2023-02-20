import client.UserClient;
import client.ZipCodeClient;
import com.github.javafaker.Faker;
import data.Response;
import data.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static data.Constants.*;

public class AddUserTest {
    private static UserClient userClient;
    private static ZipCodeClient zipCodeClient;
    private static Faker faker;

    @BeforeEach
    public void setup() {
        userClient = new UserClient();
        zipCodeClient = new ZipCodeClient();
        faker = new Faker();
    }

    // Scenario #1:
    @Test
    void addUserTest() throws IOException {
        String zipCode = zipCodeClient.getNewZipCode();
        User user = User.builder()
                .name(faker.name().firstName())
                .age(faker.number().numberBetween(1, 100))
                .sex(faker.demographic().sex().toUpperCase())
                .zipCode(zipCode)
                .build();
        int postUserStatusCode = userClient.addToUsersList(user);
        Response<List<User>> getUsersResponse = userClient.getUsersList();
        Response<List<String>> getZipCodesResponse = zipCodeClient.getZipCodesList();

        Assertions.assertAll(
                () -> Assertions.assertEquals(CREATED_STATUS, postUserStatusCode),
                () -> Assertions.assertTrue(getUsersResponse.getBody().contains(user)),
                () -> Assertions.assertFalse(getZipCodesResponse.getBody().contains(zipCode))
        );
    }

    // Scenario #2:
    @Test
    void addRequiredUserTest() throws IOException {
        User user = User.builder()
                .name(faker.name().firstName())
                .sex(faker.demographic().sex().toUpperCase())
                .build();
        int postUserStatusCode = userClient.addToUsersList(user);
        Response<List<User>> getUsersResponse = userClient.getUsersList();

        Assertions.assertAll(
                () -> Assertions.assertEquals(CREATED_STATUS, postUserStatusCode),
                () -> Assertions.assertTrue(getUsersResponse.getBody().contains(user))
        );
    }

    // Scenario #3:
    @Test
    void addUnavailableUserTest() throws IOException {
        User user = User.builder()
                .name(faker.name().firstName())
                .age(faker.number().numberBetween(1, 100))
                .sex(faker.demographic().sex().toUpperCase())
                .zipCode(faker.number().digits(5))
                .build();
        int postUserStatusCode = userClient.addToUsersList(user);
        Response<List<User>> getUsersResponse = userClient.getUsersList();

        Assertions.assertAll(
                () -> Assertions.assertEquals(DEPENDENCY_STATUS, postUserStatusCode),
                () -> Assertions.assertFalse(getUsersResponse.getBody().contains(user))
        );
    }

    // Scenario #4:
    @Test
    void addDuplicateUserTest() throws IOException {
        Response<List<User>> getUsersResponse = userClient.getUsersList();
        List<User> usersList = getUsersResponse.getBody();
        User user = User.builder()
                .name(usersList.get(0).getName())
                .sex(usersList.get(0).getSex())
                .build();
        int postUserStatusCode = userClient.addToUsersList(user);

        Assertions.assertAll(
                () -> Assertions.assertEquals(BAD_STATUS, postUserStatusCode),
                () -> Assertions.assertFalse(usersList.contains(user))
        );
    }
}
