import java.util.ArrayList;
import java.util.UUID;

public class Post {
    private String postID;

    private String title;
    private String content;
    private ArrayList<String> tags;        // also add this post after according tags
    private int likedCount;
    private ArrayList<Integer> commentIDs;  // fetch comments using commentIDs

    private String anonymousPosterName;    // user can choose a name as an input
    private String userUuid;

    public Post(String title, String content, ArrayList<String> tags, int likedCount, ArrayList<Integer> commentIDs, String anonymousPosterName, String userUuid) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.likedCount = likedCount;
        this.commentIDs = commentIDs;
        this.anonymousPosterName = anonymousPosterName;
        this.userUuid = userUuid;
        postID = UUID.randomUUID().toString();
    }

    public ArrayList<Integer> getCommentIDs() {
        return commentIDs;
    }

    public void setCommentIDs(ArrayList<Integer> commentIDs) {
        this.commentIDs = commentIDs;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void incrementLike() {
        likedCount++;
    }

    public void decrementLike() {
        likedCount--;
    }

    public String getAnonymousPosterName() {
        return anonymousPosterName;
    }

    public String getContent() {
        return content;
    }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getUserUuid() {
        return userUuid;
    }
}

