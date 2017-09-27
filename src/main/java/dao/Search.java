package dao;
//store the result of search
public class Search {
    private String name;
    private String DOB;
    private String gender;
    private String username;
    private boolean friend;

    public Search(String username, String name, String gender, String DOB)
    {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.DOB = DOB;
        this.friend = false;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setUsername(String ussernamename)
    {
        this.username = username;
    }
    public void setDOB(String DOB)
    {
        this.DOB = DOB;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    public void setFriend(boolean friend)
    {
        this.friend = friend;
    }
    public String getName()
    {
         return this.name;
    }
    public boolean getFriend()
    {
        return this.friend;
    }
    public String getUsername()
    {
        return this.username;
    }
    public String getDOB()
    {
        return this.DOB;
    }
    public String getGender()
    {
        return this.gender;
    }
}
