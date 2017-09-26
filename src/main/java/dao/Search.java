package dao;
//store the result of search
public class Search {
    private String name;
    private String DOB;
    private String gender;
    //private String username;

    public Search(String name, String gender, String DOB)
    {
        this.name = name;
        this.gender = gender;
        this.DOB = DOB;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDOB(String DOB)
    {
        this.DOB = DOB;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getName()
    {
         return this.name;
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
