package de.mtobiasz.skyrimd;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHandler {
    public static ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg"); ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user")) {
            while (rs.next()) {
                users.add(new User(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getDate(5).toLocalDate(), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        }
        return users;
    }

    public static User getRandomUser() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
             ResultSet rs = con.createStatement().executeQuery("SELECT * FROM user ORDER BY RAND() LIMIT 1")) {
            if (rs.next()) {
                return new User(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getDate(5).toLocalDate(), rs.getString(6), rs.getString(7), rs.getString(8));
            }
        }
        return null;
    }

    public static ArrayList<String> getLikes(String id) {
        ArrayList<String> likes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
             ResultSet rs = con.createStatement().executeQuery("SELECT * FROM likes WHERE fk_id1 LIKE 'id'")) {
            while (rs.next()) {
                likes.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    public static void addUser(User user){
        try(Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg")){
            PreparedStatement pstm = con.prepareStatement("INSERT INTO user(id, username, photo, location, birthday, description, gender, password) VALUES (?,?,?,?,?,?,?,?)");
            pstm.setString(1,user.getId().toString());
            pstm.setString(2,user.getUsername());
            pstm.setBlob(3,new SerialBlob(user.getPhoto()));
            pstm.setString(4,user.getLocation());
            pstm.setDate(5,Date.valueOf(user.getBirthday()));
            pstm.setString(6,user.getDescription());
            pstm.setString(7,user.getGender());
            pstm.setString(8,user.getPassword());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}