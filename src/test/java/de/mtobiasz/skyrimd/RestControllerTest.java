package de.mtobiasz.skyrimd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RestControllerTest {

    private final RestController controller = new RestController();

    @Test
    void showRandomUser() throws SQLException, JsonProcessingException {
        var expectedUser = new User(UUID.fromString("22f9d818-9be5-425e-9468-9b16fc7edecb"), "John", null, "Winterhold", LocalDate.parse("12.4.1989"),"test user", "M");

        when(DatabaseHandler.getRandomUser()).thenReturn(expectedUser);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var expectedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedUser);

        var result = controller.showRandomUser();

        assertEquals(expectedJson, result);
    }

    @Test
    void getLikes() throws SQLException, JsonProcessingException {
        var id = UUID.randomUUID();
        var expectedLikes = new ArrayList<UUID>();
        expectedLikes.add(UUID.randomUUID());
        expectedLikes.add(UUID.randomUUID());

        when(DatabaseHandler.getLikes(id)).thenReturn(expectedLikes);

        var mapper = new ObjectMapper();
        var expectedJson = mapper.writeValueAsString(expectedLikes);

        var result = controller.getLikes(id.toString());

        assertEquals(expectedJson, result);
    }

    @SneakyThrows
    @Test
    void getUser() throws SQLException {
        var id = UUID.randomUUID();
        var expectedUser = new User(UUID.fromString("22f9d818-9be5-425e-9468-9b16fc7edecb"), "John", null, "Winterhold", LocalDate.parse("12.4.1989"),"test user", "M");

        when(DatabaseHandler.getUserById(id)).thenReturn(expectedUser);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var expectedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedUser);

        var result = controller.getUser(id.toString());

        assertEquals(expectedJson, result);
    }

    @SneakyThrows
    @Test
    void getUsers() throws SQLException {
        var expectedUsers = new ArrayList<User>();
        expectedUsers.add(new User(UUID.fromString("22f9d818-9be5-425e-9468-9b16fc7edecb"), "John", null, "Winterhold", LocalDate.parse("12.4.1989"),"test user", "M"));
        expectedUsers.add(new User(UUID.fromString("b0d4af09-1671-4f1f-b876-d5120a0fb767"), "Jane", null, "Winterhold", LocalDate.parse("12.4.1989"),"test user", "M"));

        when(DatabaseHandler.getUsers()).thenReturn(expectedUsers);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var expectedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedUsers);

        var result = controller.getUsers();

        assertEquals(expectedJson, result);
    }

    @Test
    void getUsersById() throws SQLException, JsonProcessingException {
        var ids = new ArrayList<UUID>();
        ids.add(UUID.fromString("22f9d818-9be5-425e-9468-9b16fc7edecb"));
        ids.add(UUID.fromString("2"));
        var expectedUsers = new ArrayList<User>();
        expectedUsers.add(new User(UUID.fromString("22f9d818-9be5-425e-9468-9b16fc7edecb"), "John", null, "Winterhold", LocalDate.parse("12.4.1989"),"test user", "M"));
        expectedUsers.add(new User(UUID.fromString("b0d4af09-1671-4f1f-b876-d5120a0fb767"), "Jane", null, "Winterhold", LocalDate.parse("12.4.1989"),"test user", "M"));

        when(DatabaseHandler.getUsersById(ids)).thenReturn(expectedUsers);

        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var expectedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedUsers);

        var result = controller.getUsers();

        assertEquals(expectedJson, result);
    }


}
