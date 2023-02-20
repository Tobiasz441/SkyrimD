package de.mtobiasz.skyrimd;

import org.springframework.boot.jackson.JsonComponent;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

@JsonComponent
public class User {
    private UUID id;
    private String username;
    private byte[] photo;
    private String location;
    private LocalDate birthday;
    private String description;
    private String gender;
    private String password;

    public User() {
    }

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
    public User(String username, byte[] photo, String location, LocalDate birthday, String description, String gender, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.photo = photo;
        this.location = location;
        this.description = description;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
    }

    public User(UUID id,String username, byte[] photo, String location, LocalDate birthday, String description, String gender) {
        this.id = id;
        this.username = username;
        this.photo = photo;
        this.location = location;
        this.description = description;
        this.birthday = birthday;
        this.gender = gender;
    }

    public void getLikes(){

    }



    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);
        Field[] fields = this.getClass().getDeclaredFields();
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                ex.printStackTrace();
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
