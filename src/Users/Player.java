package Users;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;
    private String password;

    public Player(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
