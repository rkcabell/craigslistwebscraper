package scraper;
/**
 * @author Ryan Cabell
 * Date Created : 12/16/2020
 * Last Date Edited : 12/22/2020
 */
public class Item {
    private String postID;
    private String url;
    private String title;
    private String date;
    private String price;
    private String location;

    public Item(String postID, String url, String title, String date, String price, String location) {
        this.postID = postID;
        this.url = url;
        this.title = title;
        this.date = date;
        this.price = price;
        this.location = location;
    }

    public Item() {
        this.postID = null;
        this.url = null;
        this.title = null;
        this.date = null;
        this.price = null;
        this.location = null;
    }

    public String getPostID() {
        return postID;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "[" + postID + ", " + url + ", " + title + ", " + date + ", " + price + ", " + location + "]";
    }


}
