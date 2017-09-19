package dao;

import java.util.ArrayList;

public interface IWallPostDao {
    public boolean addWallPost(WallPost wallPost);
    public ArrayList<WallPost> getWallPosts();
    public void getPostsByUser(String username);
}
