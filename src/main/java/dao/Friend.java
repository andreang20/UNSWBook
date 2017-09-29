package dao;

public class Friend {
    private String primaryUser;
    private String secondaryUser;

    public Friend(String primaryUser, String secondaryUser) {
        this.primaryUser = primaryUser;
        this.secondaryUser = secondaryUser;
    }

    public String getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(String primaryUser) {
        this.primaryUser = primaryUser;
    }

    public String getSecondaryUser() {
        return secondaryUser;
    }

    public void setSecondaryUser(String secondaryUser) {
        this.secondaryUser = secondaryUser;
    }
}
