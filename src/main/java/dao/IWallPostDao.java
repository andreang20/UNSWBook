package dao;

import java.util.ArrayList;

public interface IWallPostDao {
    public void addWallPost(WallPost wallPost);
    public ArrayList<WallPost> getWallPosts();
    public void getPostsByUser(String username);
}
