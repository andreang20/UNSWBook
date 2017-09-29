package dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class WallPost {
    private String username;
    private int id;
    private String content;
    private Timestamp postDate;
    private String image;
    private ArrayList<Like> likes;

    public WallPost() {
        this(null, -1, null, null);
    }

    public WallPost(String username, int id, String content, Timestamp postDate) {
        this.username = username;
        this.id = id;
        this.content = content;
        this.postDate = postDate;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public boolean hasLiked(String username) {
        for (Like cur: this.getLikes()) {
            if (cur.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}

