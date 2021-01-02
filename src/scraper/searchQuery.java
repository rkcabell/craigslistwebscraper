package scraper;

/**
 * 
 * @author Ryan Cabell
 * 
 * date created: 1/1/2021
 * date edited: 1/1/2021
 */
public class searchQuery {
    private String category;
    private String subCategory;
    private String location;
    private int range;
    private int zipcode;
    //in the future can add price and extra location

    public searchQuery(String category, String subCategory, String location, int range, int zipcode) {
        this.category = category;
        this.subCategory = subCategory;
        this.location = location;
        this.range = range;
        this.zipcode = zipcode;
    }

    public searchQuery(String category, String subCategory) {
        this.category = category;
        this.subCategory = subCategory;
        this.location = null;
        this.range = -1;
        this.zipcode = -1;
    }

    public searchQuery() {
        this.category = null;
        this.subCategory = null;
        this.location = null;
        this.range = -1;
        this.zipcode = -1;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getLocation() {
        return location;
    }

    public int getRange() {
        return range;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
    
    
    
}
