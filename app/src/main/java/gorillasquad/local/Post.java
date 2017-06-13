package gorillasquad.local;

/**
 * Created by Jason on 6/13/2017.
 */

public class Post {

    private String text;
    private int timestamp;
    private int rating;

    public Post(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }
}
