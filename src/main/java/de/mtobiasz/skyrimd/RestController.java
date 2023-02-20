package de.mtobiasz.skyrimd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;

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

	@PostMapping("/api/addUser")
	public boolean addUser(@RequestBody User user){
		return DatabaseHandler.addUser(user);
	}


	/*
	@GetMapping
	public String addUser(){

	}
	*/
}
