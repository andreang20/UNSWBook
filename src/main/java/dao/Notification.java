package dao;

import java.sql.Timestamp;

public class Notification {
    private int  notificationId;
    private String username;
    private String userAction;
    private Timestamp time;
    private boolean hasSeen;

    public Notification(int notificationId, String username, String userAction, boolean hasSeen, Timestamp time) {
        this.notificationId = notificationId;
        this.username = username;
        this.userAction = userAction;
        this.hasSeen = hasSeen;
        this.time = time;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isHasSeen() {
        return hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }
}
