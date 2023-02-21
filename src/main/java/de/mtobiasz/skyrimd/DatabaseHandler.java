package de.mtobiasz.skyrimd;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHandler {
    public static User getUserById(UUID uuid) throws SQLException {
        User user = new User();
        try(Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM skyrimd.user WHERE id LIKE ?")){
            pstm.setString(1,uuid.toString());
            ResultSet rs = pstm.executeQuery();
            return new User(rs.getString(1),rs.getBytes(2),rs.getString(3), rs.getDate(4).toLocalDate(),rs.getString(5),rs.getString(6),rs.getString(7));
        }
    }

    public static ArrayList<User> getUsersById(ArrayList<UUID> uuids) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        uuids.forEach(e ->{
            try {
                users.add(getUserById(e));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return users;
    }

    public static boolean likeUserById(UUID user, UUID like) {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
            PreparedStatement pstm = con.prepareStatement("INSERT INTO skyrimd.likes(fk_id1, fk_id2) VALUES (?,?)")) {
            pstm.setString(1, user.toString());
            pstm.setString(2, like.toString());
            return true;
        } catch (SQLException e){
            return false;
        }
    }

    public static ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
             ResultSet rs = con.createStatement().executeQuery("SELECT * FROM skyrimd.user")) {
            while (rs.next()) {
                users.add(new User(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getDate(5).toLocalDate(), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
        }
        return users;
    }

    public static User getRandomUser() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
             ResultSet rs = con.createStatement().executeQuery("SELECT * FROM skyrimd.user ORDER BY RAND() LIMIT 1")) {
            if (rs.next()) {
                return new User(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getBytes(3), rs.getString(4), rs.getDate(5).toLocalDate(), rs.getString(6), rs.getString(7));
            }
        }
        return null;
    }

    //Returns
    public static ArrayList<String> getLikes(UUID id) throws SQLException {
        ArrayList<String> likes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg");
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM skyrimd.likes WHERE fk_id1 LIKE ? AND fk_id2 IN (SELECT fk_id1 FROM likes WHERE fk_id2 LIKE ?)")) {
            pstm.setString(1,id.toString());
            pstm.setString(2,id.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                likes.add(rs.getString(1));
            }
        }
        return likes;
    }

    public static boolean addUser(User user) {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg")) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO skyrimd.user(id, username, photo, location, birthday, description, gender, password) VALUES (?,?,?,?,?,?,?,?)");
            pstm.setString(1, UUID.randomUUID().toString());
            pstm.setString(2, user.getUsername());
            pstm.setBlob(3, new SerialBlob(user.getPhoto()));
            pstm.setString(4, user.getLocation());
            pstm.setDate(5, Date.valueOf(user.getBirthday()));
            pstm.setString(6, user.getDescription());
            pstm.setString(7, user.getGender());
            pstm.setString(8, user.getPassword());
            pstm.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUser(User user) {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg")) {
            PreparedStatement pstm = con.prepareStatement("UPDATE skyrimd.user SET username = ?, photo = ?,  location = ?, birthday = ?, description = ?, gender = ?, password = ? WHERE id = ?");
            pstm.setString(8, user.getId().toString());
            pstm.setString(1, user.getUsername());
            pstm.setBlob(2, new SerialBlob(user.getPhoto()));
            pstm.setString(3, user.getLocation());
            pstm.setDate(4, Date.valueOf(user.getBirthday()));
            pstm.setString(5, user.getDescription());
            pstm.setString(6, user.getGender());
            pstm.setString(7, user.getPassword());
            pstm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUser(String id) {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/skyrimd", "eisberg", "eisberg")) {
            PreparedStatement pstm = con.prepareStatement("DELETE FROM skyrimd.user WHERE id = ?");
            pstm.setString(1, id);
            pstm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}