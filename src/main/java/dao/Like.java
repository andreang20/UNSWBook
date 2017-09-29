package dao;

import java.util.ArrayList;

public class Like {
    private int wall_id;
    private String username;

    public int getWall_id() {
        return wall_id;
    }

    public void setWall_id(int wall_id) {
        this.wall_id = wall_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Like(int wall_id, String username) {
        this.wall_id = wall_id;
        this.username = username;
    }

}
