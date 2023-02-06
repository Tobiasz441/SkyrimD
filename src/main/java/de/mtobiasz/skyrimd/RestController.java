package de.mtobiasz.skyrimd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

/**
 * Angepasst aus dem Beispielprojekt
 * 
 * @author Daniel Katzberg
 *
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("/test")
	public String test() {
		return "Rest Test";
	}

	@GetMapping("/")
	public String showRandomUser() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(DatabaseHandler.getRandomUser());
		} catch (SQLException | JsonProcessingException e) {
			return "Error occurred " + e.getLocalizedMessage();
		}
	}

}
