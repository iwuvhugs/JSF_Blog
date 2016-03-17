/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author c0661047
 */
@ApplicationScoped
@ManagedBean
public class UserController {

    private List<User> users;

    public UserController() {
        this.users = new ArrayList<>();
        updateUsersFromDatabase();
    }

    private void updateUsersFromDatabase() {
        try {
            this.users = new ArrayList<>();
            // Make a connection
            Connection connection = Utils.getConnection();
            // Build a Query
            String sql = "SELECT * FROM users";
            Statement stmt = connection.createStatement();

            // Parse the Results
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash")
                );
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void registerNewUser(String username, String passhash) {
        try {
            // Make a connection
            Connection connection = Utils.getConnection();
            // Build a Query
            String sql = "INSERT INTO users (`username`, `passhash`) VALUES (?, ?)";
            Statement stmt = connection.createStatement();

            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, passhash);
            pstmt.executeUpdate();
            updateUsersFromDatabase();

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<User> getUsers() {
        updateUsersFromDatabase();
        return users;
    }

    public String getUsernameById(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u.getUsername();
            }
        }
        return null;
    }

}
