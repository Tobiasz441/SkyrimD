package de.mtobiasz.skyrimd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.mtobiasz.skyrimd.JsonClasses.UserId;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("/api/randUser")
	public String showRandomUser() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getRandomUser());
		} catch (SQLException | JsonProcessingException e) {
			Map<String,String> payload = new HashMap<>();
			payload.put("error", String.valueOf(true));
			payload.put("message",e.getLocalizedMessage());

			try {
				return new ObjectMapper().writeValueAsString(payload);
			} catch (JsonProcessingException je){
				return "{error = \"true\", message = \"" + e.getLocalizedMessage() + "\"";
			}
		}
	}

	@GetMapping("/api/getLikes")
	public String getLikes(@PathVariable String  id) {
		var mapper = new ObjectMapper();
		ArrayList<UserId> jsonList = new ArrayList<>();
		try {
			ArrayList<UUID> likes = DatabaseHandler.getLikes(UUID.fromString(id));
			for (UUID like : likes) {
				jsonList.add(new UserId(like));
			}
			return mapper.writeValueAsString(jsonList);
		} catch (JsonProcessingException | SQLException e) {
			Map<String,String> payload = new HashMap<>();
			payload.put("error", String.valueOf(true));
			payload.put("message",e.getLocalizedMessage());

			try {
				return new ObjectMapper().writeValueAsString(payload);
			} catch (JsonProcessingException je){
				return "{error = \"true\", message = \"" + e.getLocalizedMessage() + "\"";
			}
		}

	}

	@GetMapping("/api/getUser")
	public String getUser(@PathVariable String id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getUserById(UUID.fromString(id)));
		} catch (SQLException | JsonProcessingException e) {
			Map<String,String> payload = new HashMap<>();
			payload.put("error", String.valueOf(true));
			payload.put("message",e.getLocalizedMessage());

			try {
				return new ObjectMapper().writeValueAsString(payload);
			} catch (JsonProcessingException je){
				return "{error = \"true\", message = \"" + e.getLocalizedMessage() + "\"";
			}
		}
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/getUsers")
	public String getUsers() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getUsers());
		} catch (SQLException | JsonProcessingException e) {
			Map<String,String> payload = new HashMap<>();
			payload.put("error", String.valueOf(true));
			payload.put("message",e.getLocalizedMessage());

			try {
				return new ObjectMapper().writeValueAsString(payload);
			} catch (JsonProcessingException je){
				return "{error = \"true\", message = \"" + e.getLocalizedMessage() + "\"";
			}
		}
	}

	@GetMapping("/api/getUsersId")
	public String getUsersById(@RequestParam ArrayList<String> ids) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		ArrayList<UUID> uuid = new ArrayList<>();
		for (String id: ids) {
			uuid.add(UUID.fromString(id));
		}
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DatabaseHandler.getUsersById(uuid));
		} catch (SQLException | JsonProcessingException e) {
			Map<String,String> payload = new HashMap<>();
			payload.put("error", String.valueOf(true));
			payload.put("message",e.getLocalizedMessage());

			try {
				return new ObjectMapper().writeValueAsString(payload);
			} catch (JsonProcessingException je){
				return "{error = \"true\", message = \"" + e.getLocalizedMessage() + "\"";
			}
		}
	}

	@PostMapping("/api/like")
	public boolean likeUser(@RequestParam String id, @RequestParam String like) {
		try {
			DatabaseHandler.likeUserById(UUID.fromString(id), UUID.fromString(like));
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@PostMapping("/api/addUser")
	public boolean addUser(@RequestParam String username, @RequestParam byte[] photo, @RequestParam String location, @RequestParam LocalDate birthday, @RequestParam String description, @RequestParam String gender, @RequestParam String password){
		try {
			DatabaseHandler.addUser(new User(UUID.randomUUID(), username, photo, location, birthday, description, gender, password ));
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@DeleteMapping("/api/deleteUser")
	public boolean deleteUser(@PathVariable String id) {
		try {
			DatabaseHandler.deleteUser(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PutMapping("/api/updateUser")
	public boolean updateUser(@RequestParam User user){
		try {
			DatabaseHandler.updateUser(user);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
