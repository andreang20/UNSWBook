package dao;

public class Verification {
    private String username;
    private boolean is_verified;
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Verification(String username, boolean is_verified, String code) {
        this.username = username;
        this.is_verified = is_verified;
        this.code = code;

    }
}
