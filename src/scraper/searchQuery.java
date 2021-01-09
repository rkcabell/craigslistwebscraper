package scraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Ryan Cabell
 * 
 * date created: 1/1/2021
 * date edited: 1/9/2021
 */
public class searchQuery {
    //in the future can add price and extra location    
    private String category;
    private String subCategory;
    private String location;
    private int range;
    private int zipcode;
    private static final Map<String, String> commOptions = new HashMap<>();
    private static final Map<String, String> gigsOptions = new HashMap<>();
    private static final Map<String, String> housOptions = new HashMap<>();
    private static final Map<String, String> jobsOptions = new HashMap<>();
    private static final Map<String, String> saleOptions = new HashMap<>();
    private static final Map<String, String> servOptions = new HashMap<>();
    private static final Map<String, String> dfltOptions = new HashMap<>();
    private static final List<Map<String, String>> options = new ArrayList<Map<String, String>>();
    static {
        dfltOptions.put("all", "default");
        
        commOptions.putAll(dfltOptions);
        commOptions.put("activities", "activity-partners");
        commOptions.put("artists", "artists");
        commOptions.put("childcare", "childcare");
        commOptions.put("classes", "classes");
        commOptions.put("events", "events");
        commOptions.put("general", "general-community");
        commOptions.put("groups", "groups");
        commOptions.put("local news", "local-news-and-views");
        commOptions.put("lost + found", "lost-found");
        commOptions.put("missed connections", "missed-connections");
        commOptions.put("musicians", "musicians");
        commOptions.put("pets", "pets");
        commOptions.put("politics", "politics");
        commOptions.put("rants & raves", "rants-raves");
        commOptions.put("rideshare", "rideshare");
        commOptions.put("volunteers", "volunteers");
        
        servOptions.putAll(dfltOptions);
        servOptions.put("automotive", "automotive-services");
        servOptions.put("beauty", "beauty-services");
        servOptions.put("cell/mobile", "cell-phone-mobile-services");
        servOptions.put("computer", "computer-services");
        servOptions.put("creative", "creative-services");
        servOptions.put("cycle", "cycle-services");
        servOptions.put("event", "event-services");
        servOptions.put("farm + garden", "farm-garden-services");
        servOptions.put("financial", "financial-services");
        servOptions.put("household", "household-services");
        servOptions.put("labor/move", "labor-hauling-moving");
        servOptions.put("legal", "legal-services");
        servOptions.put("lessons", "lessos-tutoring");
        servOptions.put("marine", "marine-services");
        servOptions.put("pet", "pet-services");
        servOptions.put("real estate", "real-estate-services");
        servOptions.put("skilled trade", "skilled-trade-services");
        servOptions.put("sm biz ads", "small-biz-ads");
        servOptions.put("travel/vac", "travel-vacation-services");
        servOptions.put("wite/ed/tran", "writing-editing-translation");
        
        housOptions.putAll(dfltOptions);
        housOptions.put("apts/housing", "apartments-housing-for-rent");
        housOptions.put("housin swap", "housing-swap");
        housOptions.put("housing wanted", "all-housing-wanted");
        housOptions.put("office/commerical", "office-commercial");
        housOptions.put("parking/storage", "parking-storage");
        housOptions.put("real estate for sale", "real-estate");
        housOptions.put("rooms/shared", "room-shares");
        housOptions.put("rooms wanted", "wanted%3A-room-share");
        housOptions.put("sublets/temporary", "sublets-temporary");
        housOptions.put("vacation rentals", "vacation-rentals");

        saleOptions.putAll(dfltOptions);
        saleOptions.put("antiques", "antiques");
        saleOptions.put("appliances", "appliances");
        saleOptions.put("arts + crafts", "arts-crafts");
        saleOptions.put("atv/uts/sno", "atvs%2C-utvs%2C-snowmobiles");
        saleOptions.put("auto parts", "auto-parts");
        saleOptions.put("aviation", "aviation");
        saleOptions.put("baby + kid", "baby-kid-stuff");
        saleOptions.put("barter", "barter");
        saleOptions.put("beauty + hlth", "health-and-beauty");
        saleOptions.put("bike parts", "bicycle-parts");
        saleOptions.put("bikes", "bicycles");
        saleOptions.put("boat parts", "boat-parts-accessories");
        saleOptions.put("boats", "boats");
        saleOptions.put("books", "books-magazines");
        saleOptions.put("business", "business");
        saleOptions.put("cars + trucks", "cars-trucks");
        saleOptions.put("cds/dvd/vhs", "cds-dvds-vhs");
        saleOptions.put("cell phones", "cell-phones");
        saleOptions.put("clothes + acc", "clothing-accessories");
        saleOptions.put("collectibles", "collectibles");
        saleOptions.put("computer parts", "computer-parts");
        saleOptions.put("computers", "computers");
        saleOptions.put("electronics", "electronics");
        saleOptions.put("farm + garden", "farm-garden");
        saleOptions.put("free", "free-stuff");
        saleOptions.put("furniture", "furniture");
        saleOptions.put("garage sale", "garage-moving-sales");
        saleOptions.put("general", "general-for-sale");
        saleOptions.put("heavy equip", "heavy-equipment");
        saleOptions.put("household", "household-items");
        saleOptions.put("jewelry", "jewelry");
        saleOptions.put("materials", "materials");
        saleOptions.put("motorcycle parts", "motorcycle-parts-accessories");
        saleOptions.put("motorcycles", "motorcycles-scooters");
        saleOptions.put("music instr", "musical-instruments");
        saleOptions.put("photo + video", "photo-video");
        saleOptions.put("rvs + camp", "recreational-vehicles");
        saleOptions.put("sporting", "sporting-goods");
        saleOptions.put("tickets", "tickets");
        saleOptions.put("tools", "tools");
        saleOptions.put("toys + games", "toys-games");
        saleOptions.put("trailers", "trailers");
        saleOptions.put("video gaming", "video-gaming");
        saleOptions.put("wanted", "wanted");
        saleOptions.put("wheels + tires", "auto-wheels-tires");

        jobsOptions.putAll(dfltOptions);
        jobsOptions.put("accounting + finance", "accounting-finance");
        jobsOptions.put("admin/office", "admin-office");
        jobsOptions.put("arch/engineering", "architect-engineer-cad");
        jobsOptions.put("art/media/design", "art-media-design");
        jobsOptions.put("biotech/science", "science-biotech");
        jobsOptions.put("business/mgmt", "business-mgmt");
        jobsOptions.put("customer service", "customer-service");
        jobsOptions.put("education", "education-teaching");
        jobsOptions.put("etc/misc", "et-cetera");
        jobsOptions.put("food/bev/hosp", "food-beverage-hospitality");
        jobsOptions.put("general labor", "general-labor");
        jobsOptions.put("government", "government");
        jobsOptions.put("human resources", "human-resources");
        jobsOptions.put("legal/paralegal", "legal-paralegal");
        jobsOptions.put("manufacturing", "manufacturing");
        jobsOptions.put("marketing/pr/ad", "marketing-advertising-pr");
        jobsOptions.put("medical/health", "healthcare");
        jobsOptions.put("nonprofit sector", "nonprofit");
        jobsOptions.put("real estate", "real-estate");
        jobsOptions.put("retail/wholesale", "retail-wholesale");
        jobsOptions.put("sales/biz dev", "sales");
        jobsOptions.put("salon/spa/fitness", "salon-spa-fitness");
        jobsOptions.put("security", "security");
        jobsOptions.put("skilled trade/craft", "skilled-trades-artisan");
        jobsOptions.put("software/qa/dba", "software-qa-dba-etc");
        jobsOptions.put("systems/network", "systems-networking");
        jobsOptions.put("technical support", "technical-support");
        jobsOptions.put("transport", "transportation");
        jobsOptions.put("tv/film/video", "tv-film-video-radio");
        jobsOptions.put("web/info design", "web-html-info-design");
        jobsOptions.put("writing/editing", "writing-editing");

        gigsOptions.putAll(dfltOptions);
        gigsOptions.put("computer", "computer-gigs");
        gigsOptions.put("creative", "creative-gigs");
        gigsOptions.put("crew", "crew-gigs");
        gigsOptions.put("domestic", "domestic-gigs");
        gigsOptions.put("event", "event-gigs");
        gigsOptions.put("labor", "labor-gigs");
        gigsOptions.put("talent", "talent-gigs");
        gigsOptions.put("writing", "writing-gigs");
        
        options.add(dfltOptions);
        options.add(commOptions);
        options.add(gigsOptions);
        options.add(housOptions);
        options.add(jobsOptions);
        options.add(saleOptions);
        options.add(servOptions);
    }

    public searchQuery(String category, String subCategory, String location) {
        this.category = category;
        this.subCategory = subCategory;
        this.location = location;
        this.range = -1;
        this.zipcode = -1;
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
        if (category == null)
            return "";
        return category;
    }

    public String getSubCategory() {
        if (subCategory == null)
            return "";
        return subCategory;
    }

    public String getLocation() {
        if(location == null)
            return "";
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

    public static List<Map<String, String>> getOptions() {
        return options;
    }
    
    public static String getOptionData(String cat, String subcat, int option){
        return null;
    }

    @Override
    public String toString() {
        return "searchQuery{" + "category=" + category + ", subCategory=" + subCategory + ", location=" + location + ", range=" + range + ", zipcode=" + zipcode + '}';
    }
}