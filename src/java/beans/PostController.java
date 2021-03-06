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
public class PostController {

    private List<Post> posts;
    private Post currentPost;

    public List<Post> getPosts() {
        updatePostsFromDatabase();
        return posts;
    }

    public Post getCurrentPost() {
        return currentPost;
    }

    public PostController() {
        this.posts = new ArrayList<>();
        updatePostsFromDatabase();
    }

    private void updatePostsFromDatabase() {
        try {
            this.posts = new ArrayList<>();
            // Make a connection
            Connection connection = Utils.getConnection();
            // Build a Query
            String sql = "SELECT * FROM posts";
            Statement stmt = connection.createStatement();

            // Parse the Results
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getTimestamp("created_time"),
                        rs.getString("contents")
                );
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Navigation Methods
    public String viewPost(Post post) {
        currentPost = post;
        return "viewPost";
    }

    public String editPost(Post post) {
        currentPost = post;
        return "editPost";
    }

    public String savePost() {

        try {
            // Make a connection
            Connection connection = Utils.getConnection();
            // Build a Query
            String sql = "UPDATE posts SET title = ?, contents = ? WHERE id = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, currentPost.getTitle());
            pstmt.setString(2, currentPost.getContents());
            pstmt.setInt(3, currentPost.getId());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "viewPost";
    }

    public String addPost(int user_id, String title, String content) {
        try {
            // Make a connection
            Connection connection = Utils.getConnection();

//            "INSERT INTO `posts`(`user_id`, `title`, `contents`) VALUES (1,\"Hi\",\"Hi\")";
            String sql = "INSERT INTO posts (`user_id`, `title`, `contents`) VALUES (?, ?, ?) ";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, user_id);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
//             System.out.println(pstmt.toString());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public String deleteCurrentPost() {

        try {
            // Make a connection
            Connection connection = Utils.getConnection();

            String sql = "DELETE FROM posts WHERE id = ? ";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, currentPost.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index";
    }
}
