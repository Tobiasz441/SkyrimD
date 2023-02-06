package de.mtobiasz.skyrimd;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private byte[] photo;
    private String location;
    private LocalDate birthday;
    private String description;
    private String gender;
    private String password;

    public User(UUID id, String username, byte[] photo, String location, LocalDate birthday, String description, String gender, String password) {
        this.id = id;
        this.username = username;
        this.photo = photo;
        this.location = location;
        this.description = description;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
    }

    public void getLikes(){

    }
}
