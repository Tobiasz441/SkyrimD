package de.mtobiasz.skyrimd;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *  Abgeändert von dem Rest Beispiel Projekt
 * @author Daniel Katzberg
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SkyrimDApplication.class);
	}

}
