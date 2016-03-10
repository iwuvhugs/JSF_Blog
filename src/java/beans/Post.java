/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 *
 * CREATE TABLE posts ( id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, user_id INT
 * NOT NULL, title VARCHAR(255) NOT NULL UNIQUE, created_time DATETIME NOT NULL,
 * contents TEXT, FOREIGN KEY posts(user_id) REFERENCES users(id) );
 *
 * @author c0661047
 */
public class Post {

    private int id;
    private int user_id;
    private String title;
    private Date createdTime;
    private String contents;

    public Post(int id, int user_id, String title, Date createdTime, String contents) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.createdTime = createdTime;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
