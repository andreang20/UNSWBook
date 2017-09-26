package dao;

import java.sql.Timestamp;

public class Log {
    private String username;
    private String action;
    private Timestamp time;

    public Log(String username, String action, Timestamp time) {
        this.username = username;
        this.action = action;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
