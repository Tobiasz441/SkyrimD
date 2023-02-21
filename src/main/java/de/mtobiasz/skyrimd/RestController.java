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
	public String getLikes(@PathVariable String id) throws JsonProcessingException, SQLException {
		var mapper = new ObjectMapper();
		ArrayList<UserId> jsonList = new ArrayList<>();
		ArrayList<String> likes = DatabaseHandler.getLikes(UUID.fromString(id));
		for (String like : likes) {
			jsonList.add(new UserId(like));
		}
		return mapper.writeValueAsString(jsonList);
	}

	@PostMapping("/api/addUser")
	public boolean addUser(@RequestBody User user){
		return true;
		//return DatabaseHandler.addUser(user);
	}

	@DeleteMapping("/api/deleteUser")
	void deleteEmployee(@PathVariable String id) {
		DatabaseHandler.deleteUser(id);
	}

	@PutMapping("/api/updateUser")
	void updateUser(@RequestBody User user){
		DatabaseHandler.updateUser(user);
	}
}
