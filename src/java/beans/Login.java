/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author c0661047
 */
@SessionScoped
@ManagedBean
public class Login {

    private String username;
    private String password;
    
    private boolean loggedIn;
    private User loggedUser;

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public User getLoggedUser(){
        return loggedUser;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Login() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String doLogin() {
        String passhash = Utils.hash(password);

        for (User u : new UserController().getUsers()) {    
            if (username.equals(u.getUsername())
                    && passhash.equals(u.getPasshash())) {
                loggedIn = true;
                loggedUser = u;
                return "index";
            }
        }
        loggedIn = false;
        loggedUser = null;
        return "login";

    }
    public String doLogout(){
        loggedIn = false;
        loggedUser = null;
        return "index";
    }
}
