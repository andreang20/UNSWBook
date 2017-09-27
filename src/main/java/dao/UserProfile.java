package dao;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class UserProfile {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private Date date;
    private int session_id;
    private boolean is_banned;

    public boolean getIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public UserProfile() {
        this(null,null,null,null,null,null,null, -1, false);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserProfile(String username, String password, String firstname, String lastname,
                       String email, String gender, Date date, int session_id, boolean is_banned) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.date = date;
        this.session_id = session_id;
        this.is_banned = is_banned;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public String getFormattedDate() {
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        return targetFormat.format(this.date);
    }
}
