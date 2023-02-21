package de.mtobiasz.skyrimd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.mtobiasz.skyrimd.JsonClasses.UserId;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("/test")
	public String test() {
		return "Rest Test";
	}

	@GetMapping("/api/randUser")
	public String showRandomUser() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getRandomUser());
		} catch (SQLException | JsonProcessingException e) {
			return "Error occurred " + e.getLocalizedMessage();
		}
	}

	@GetMapping("/api/getLikes")
	public String getLikes(@PathVariable String  id) {
		var mapper = new ObjectMapper();
		ArrayList<UserId> jsonList = new ArrayList<>();
		ArrayList<UUID> likes = DatabaseHandler.getLikes(UUID.fromString(id));
		for (UUID like : likes) {
			jsonList.add(new UserId(like));
		}
		try {
			return mapper.writeValueAsString(jsonList);
		} catch (JsonProcessingException e) {
			return "Error occurred " + e.getLocalizedMessage();
		}

	}

	@GetMapping("/api/getUser")
	public String getUser(@PathVariable UUID id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getUserById(id));
		} catch (SQLException | JsonProcessingException e) {
			return "Error occurred " + e.getLocalizedMessage();
		}
	}

	@GetMapping("/api/getUsers")
	public String getUsers() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getUsers());
		} catch (SQLException | JsonProcessingException e) {
			return "Error occurred " + e.getLocalizedMessage();
		}
	}

	@PostMapping("/api/like")
	public boolean likeUser(@RequestBody UUID id, @RequestBody UUID like) {
		return DatabaseHandler.likeUserById(id, like);
	}

	@PostMapping("/api/addUser")
	public boolean addUser(@RequestBody User user){
		return DatabaseHandler.addUser(user);
	}

	@DeleteMapping("/api/deleteUser")
	void deleteUser(@PathVariable String id) {
		DatabaseHandler.deleteUser(id);
	}

	@PutMapping("/api/updateUser")
	void updateUser(@RequestBody User user){
		DatabaseHandler.updateUser(user);
	}
}
