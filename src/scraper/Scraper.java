package scraper;

/**
 * @author Ryan Cabell Date Created : 12/16/2020 Last Date Edited : 1/1/2021
 *
 * Imports: jsoup: Java HTML Parser can use main craigslist html page to get
 * hrefs
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    private String url;
    private final String WEBSITE = "craigslist.org";
    private final String PREFIX = "https://";
    
    public Item[] scrape(searchQuery newSearch) throws MalformedURLException {
        //checking if empty should be in controller
        if (newSearch.getLocation().isEmpty()) {
            System.out.println("Accessing default location,");
        } else {
            System.out.println("Accessing location [" + newSearch.getLocation()
                    + "] and section [" + newSearch.getCategory()+ ", " 
                    + newSearch.getSubCategory() + "]");
        }

        String link = accessPage(newSearch);
        String pageTitle;

        try {
            Document doc = Jsoup.connect(link).get();
            pageTitle = doc.title();

            //get info from each listing (element)
            Item[] listOfItems = new Item[getRangeTo(newSearch)];
            Elements divData = doc.select("ul.rows li div");

            //Uses CSS Selectors:
            //https://jsoup.org/cookbook/extracting-data/selector-syntax
            //add Item(pid, url, title, date, price, location)
            int iterator = 0;
            for (Element currentElement : divData) {
                listOfItems[iterator] = (new Item(
                        currentElement.select("div a[data-id]").attr("data-id"),
                        currentElement.select("div a[data-id]").attr("href"),
                        currentElement.select("div a[data-id]").text(),
                        currentElement.select("div time[datetime]").attr("title"),
                        currentElement.select("div .result-price").text(),
                        currentElement.select("div .result-hood").text()));
                iterator++;
            }
            //System.out.println(Arrays.toString(listOfItems));
            
            return listOfItems;
        } catch (IOException ex) {
        } catch (Exception ex) {
        }
        return new Item[1];
    }

    private String accessPage(searchQuery newSearch) throws MalformedURLException {
        
        String loc = newSearch.getLocation();
        String cat = newSearch.getCategory();
        String subcat = newSearch.getSubCategory();
        int range = newSearch.getRange();
        int zip = newSearch.getZipcode();
        
        if (range > -1 && zip > -1) {
            System.out.println("Accessing " + cat + ", subcategory "
                    + subcat + ", at " + loc + " with range of "
                    + range + " miles from zipcode " + zip);
            url = generateBaseUrl(loc, cat, subcat, range, zip);
        }
        else{
            System.out.print("range and/or zipcode invalid.");
            System.out.println(" Accessing " + cat + ", subcategory "
                    + subcat + ", at " + loc);
            url = generateBaseUrl(loc, cat, subcat);
        }
        
        return url;
    }
    
    public int getRangeTo(searchQuery newSearch) throws MalformedURLException {

        /*
        *This block goes down the classes of the search legend to eventually get
        *the number of items listed on the page
        */ 
        String url = accessPage(newSearch);
        try {
            Document doc = Jsoup.connect(url).get();
        
        Elements range = doc.select("div.search-legend div");
        range = range.select("div.paginator.buttongroup.firstpage span");
        range = range.select("span.buttons span");
        range = range.select("span.button.pagenum span");
        range = range.select("span.range span");

        String rangeTo = range.select("span.rangeTo").text();
        //Using breakpoint I can see "span.rangeTo" has 2 identical items in array

        /*rangeTo example = "120 120".
      *Cut the string after the space (could  be before space)*/
        rangeTo = rangeTo.substring(rangeTo.indexOf(" ") + 1);
        return Integer.parseInt(rangeTo);
        }
        catch(Exception e){}
        return -1;
    }

    private static void displayHTML(Document doc) {
        System.out.println(doc.outerHtml());
    }

    private static void displayAllElements(Document doc) {
        System.out.println(doc.select("ul.rows li").eachText());
    }
    
    private String generateBaseUrl(String location, String category, String subCategory) {
        String temp = WEBSITE;
        if(location.equals(""))
            location = "default";
        
        if (!(location.equalsIgnoreCase("default"))) {
            temp = PREFIX + location + "." + WEBSITE;
        }
        else{
            //needs to use some location
            //right now there is a throw in controller that stops this from running
        }
        if (subCategory.equals("default")) {
            temp += "/d/" + category + "/search/" + getCatId(category);
        } else {
            temp += "/d/" + subCategory + "/search/" + getSubCatId(category, subCategory);
        }
        return temp;
    }

    private String generateBaseUrl(String location, String category, String subCategory, int range, int zip) {
        //error: sale/wanted instead of for-sale being passed
        String temp = WEBSITE;
        if(location.equals(""))
            location = "default";
        
        if (!(location.equalsIgnoreCase("default"))) {
            temp = PREFIX + location + "." + WEBSITE;
        }
        if (subCategory.equals("all")) {
            temp += "/d/" + category + "/search/" + getCatId(category);                     //add range from zip
        } else {
            temp += "/d/" + subCategory + "/search/" + getSubCatId(category, subCategory);  //add range from zip
        }
        return temp;
    }

    private String getCatId(String category) {
        switch (category) {
            case "community":
                return "ccc";
            case "services":
                return "bbb";
            case "housing":
                return "hhh";
            case "for-sale":
                return "sss";
            case "jobs":
                return "jjj";
            case "gigs":
                return "ggg";
            case "resumes":
                return "rrr";
        }
        return null;
    }

    private String getSubCatId(String category, String subCategory) {
        String id = null;
        switch (category) {
            case "community":
                switch (subCategory) {
                    case "activity-partners":
                        id = "act";
                        break;
                    case "artists":
                        id = "ats";
                        break;
                    case "childcare":
                        id = "kid";
                        break;
                    case "classes":
                        id = "cls";
                        break;
                    case "events":
                        id = "eve";
                        break;
                    case "general-community":
                        id = "com";
                        break;
                    case "groups":
                        id = "grp";
                        break;
                    case "local-news-and-views":
                        id = "vnn";
                        break;
                    case "lost-found":
                        id = "laf";
                        break;
                    case "missed-connections":
                        id = "mis";
                        break;
                    case "musicians":
                        id = "muc";
                        break;
                    case "pets":
                        id = "pet";
                        break;
                    case "politics":
                        id = "pol";
                        break;
                    case "rants-raves":
                        id = "rnr";
                        break;
                    case "rideshare":
                        id = "rid";
                        break;
                    case "volunteers":
                        id = "vol";
                        break;
                }
            case "services":
                switch (subCategory) {
                    case "automotive-services":
                        id = "aos";
                        break;
                    case "beauty-services":
                        id = "bts";
                        break;
                    case "cell-phone-mobile-services":
                        id = "cms";
                        break;
                    case "computer-services":
                        id = "cps";
                        break;
                    case "creative-services":
                        id = "crs";
                        break;
                    case "cycle-services":
                        id = "cys";
                        break;
                    case "event-services":
                        id = "evs";
                        break;
                    case "farm-garden-services":
                        id = "fgs";
                        break;
                    case "financial-services":
                        id = "fns";
                        break;
                    case "household-services":
                        id = "hss";
                        break;
                    case "labor-hauling-moving":
                        id = "lbs";
                        break;
                    case "legal-services":
                        id = "lgs";
                        break;
                    case "lessos-tutoring":
                        id = "lss";
                        break;
                    case "marine-services":
                        id = "mas";
                        break;
                    case "pet-services":
                        id = "pas";
                        break;
                    case "real-estate-services":
                        id = "rts";
                        break;
                    case "skilled-trade-services":
                        id = "sks";
                        break;
                    case "small-biz-ads":
                        id = "biz";
                        break;
                    case "travel-vacation-services":
                        id = "trv";
                        break;
                    case "writing-editing-translation":
                        id = "wet";
                        break;
                }
            case "housing":
                switch (subCategory) {
                    case "apartments-housing-for-rent":
                        id = "apa";
                        break;
                    case "housing-swap":
                        id = "swp";
                        break;
                    case "all-housing-wanted":
                        id = "hsw";
                        break;
                    case "office-commercial":
                        id = "off";
                        break;
                    case "parking-storage":
                        id = "prk";
                        break;
                    case "real-estate":
                        id = "rea";
                        break;
                    case "room-shares":
                        id = "roo";
                        break;
                    case "wanted%3A-room-share":
                        id = "sha";
                        break;
                    case "sublets-temporary":
                        id = "sub";
                        break;
                    case "vacation-rentals":
                        id = "vac";
                        break;
                }
            case "for-sale":
                switch (subCategory) {
                    case "antiques":
                        id = "ata";
                        break;
                    case "appliances":
                        id = "ppa";
                        break;
                    case "arts-crafts":
                        id = "ara";
                        break;
                    case "atvs%2C-utvs%2C-snowmobiles":
                        id = "sna";
                        break;
                    case "auto-parts":
                        id = "pta";
                        break;
                    case "aviation":
                        id = "ava";
                        break;
                    case "baby-kid-stuff":
                        id = "baa";
                        break;
                    case "barter":
                        id = "bar";
                        break;
                    case "health-and-beauty":
                        id = "haa";
                        break;
                    case "bicycle-parts":
                        id = "bip";
                        break;
                    case "bicycles":
                        id = "bia";
                        break;
                    case "boat-parts-accessories":
                        id = "bpa";
                        break;
                    case "boats":
                        id = "boo";
                        break;
                    case "books-magazines":
                        id = "bka";
                        break;
                    case "business":
                        id = "bfa";
                        break;
                    case "cars-trucks":
                        id = "cta";
                        break;
                    case "cds-dvds-vhs":
                        id = "ema";
                        break;
                    case "cell-phones":
                        id = "moa";
                        break;
                    case "clothing-accessories":
                        id = "cla";
                        break;
                    case "collectibles":
                        id = "cba";
                        break;
                    case "computer-parts":
                        id = "syp";
                        break;
                    case "computers":
                        id = "sya";
                        break;
                    case "electronics":
                        id = "ela";
                        break;
                    case "farm-garden":
                        id = "gra";
                        break;
                    case "free-stuff":
                        id = "zip";
                        break;
                    case "furniture":
                        id = "fua";
                        break;
                    case "garage-moving-sales":
                        id = "gms";
                        break;
                    case "general-for-sale":
                        id = "foa";
                        break;
                    case "heavy-equipment":
                        id = "hva";
                        break;
                    case "household-items":
                        id = "hsa";
                        break;
                    case "jewelry":
                        id = "jwa";
                        break;
                    case "materials":
                        id = "maa";
                        break;
                    case "motorcycle-parts-accessories":
                        id = "mpa";
                        break;
                    case "motorcycles-scooters":
                        id = "mca";
                        break;
                    case "musical-instruments":
                        id = "msa";
                        break;
                    case "photo-video":
                        id = "pha";
                        break;
                    case "recreational-vehicles":
                        id = "rva";
                        break;
                    case "sporting-goods":
                        id = "sga";
                        break;
                    case "tickets":
                        id = "tia";
                        break;
                    case "tools":
                        id = "tla";
                        break;
                    case "toys-games":
                        id = "taa";
                        break;
                    case "trailers":
                        id = "tra";
                        break;
                    case "video-gaming":
                        id = "vga";
                        break;
                    case "wanted":
                        id = "waa";
                        break;
                    case "auto-wheels-tires":
                        id = "wta";
                        break;

                }
            case "jobs":
                switch (subCategory) {
                    case "accounting-finance":
                        id = "acc";
                        break;
                    case "admin-office":
                        id = "ofc";
                        break;
                    case "architect-engineer-cad":
                        id = "egr";
                        break;
                    case "art-media-design":
                        id = "med";
                        break;
                    case "science-biotech":
                        id = "sci";
                        break;
                    case "business-mgmt":
                        id = "bus";
                        break;
                    case "customer-service":
                        id = "csr";
                        break;
                    case "education-teaching":
                        id = "edu";
                        break;
                    case "et-cetera":
                        id = "etc";
                        break;
                    case "food-beverage-hospitality":
                        id = "fbh";
                        break;
                    case "general-labor":
                        id = "lab";
                        break;
                    case "government":
                        id = "gov";
                        break;
                    case "human-resources":
                        id = "hum";
                        break;
                    case "legal-paralegal":
                        id = "lgl";
                        break;
                    case "manufacturing":
                        id = "mnu";
                        break;
                    case "marketing-advertising-pr":
                        id = "mar";
                        break;
                    case "healthcare":
                        id = "hea";
                        break;
                    case "nonprofit":
                        id = "npo";
                        break;
                    case "real-estate":
                        id = "rej";
                        break;
                    case "retail-wholesale":
                        id = "ret";
                        break;
                    case "sales":
                        id = "sls";
                        break;
                    case "salon-spa-fitness":
                        id = "spa";
                        break;
                    case "security":
                        id = "sec";
                        break;
                    case "skilled-trades-artisan":
                        id = "trd";
                        break;
                    case "software-qa-dba-etc":
                        id = "sof";
                        break;
                    case "systems-networking":
                        id = "sad";
                        break;
                    case "technical-support":
                        id = "tch";
                        break;
                    case "transportation":
                        id = "trp";
                        break;
                    case "tv-film-video-radio":
                        id = "tfr";
                        break;
                    case "web-html-info-design":
                        id = "web";
                        break;
                    case "writing-editing":
                        id = "wri";
                        break;
                }
            case "gigs":
                switch (subCategory) {
                    case "computer-gigs":
                        id = "cpg";
                        break;
                    case "creative-gigs":
                        id = "crg";
                        break;
                    case "crew-gigs":
                        id = "cwg";
                        break;
                    case "domestic-gigs":
                        id = "dmg";
                        break;
                    case "event-gigs":
                        id = "evg";
                        break;
                    case "labor-gigs":
                        id = "lbg";
                        break;
                    case "talent-gigs":
                        id = "tlg";
                        break;
                    case "writing-gigs":
                        id = "wrg";
                        break;
                }
        }
        return id;
    }

}
