package de.mtobiasz.skyrimd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}