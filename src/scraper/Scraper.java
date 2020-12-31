package scraper;

/**
 * @author Ryan Cabell Date Created : 12/16/2020 Last Date Edited : 12/22/2020
 *
 * Imports: jsoup: Java HTML Parser
 * can use main craigslist html page to get hrefs
 */
import java.io.IOException;
import javafx.application.Application;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
    private String url;
    private String website = "craigslist.org";
    

    private void scrape(String location, String category, String subCategory, int range, int zip) {

        System.out.println("Printing " + category + ", subcategory " + 
                subCategory + ", at " + location + " with range of " + 
                range + " miles from zipcode " + zip);
        
        url = generateBaseUrl(location, category, subCategory);
        
    }

    /*
  *This block goes down the classes of the search legend to eventually get
  *the number of items listed on the page
     */
    private static int getRangeTo(Document doc) {

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

    /**
     * Display all HTML code on page
     *
     * @param doc
     */
    private static void displayHTML(Document doc) {
        System.out.println(doc.outerHtml());
    }

    /**
     * Display all Craigslist listings texts on a page
     *
     * @param doc
     */
    private static void displayAllElements(Document doc) {
        System.out.println(doc.select("ul.rows li").eachText());
    }

    private static void showInfo(String location, String section) {

        if (location.isEmpty()) {
            System.out.println("Accessing default location,");
        } else {
            System.out.println("Accessing location [" + location
                    + "] and section [" + section + "]");
        }
        if (section.isEmpty()) {
            System.out.println("!!! section may not have been specified !!!");
        }

        String url = "https://" + location + "craigslist.org/" + section;
        String pageTitle;

        try {
            Document doc = Jsoup.connect(url).get();
            pageTitle = doc.title();

            //get info from each listing (element)
            Item[] listOfItems = new Item[getRangeTo(doc)];
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
            for (Item item : listOfItems) {
                System.out.println(item.toString());
            }

        } catch (IOException ex) {
        } catch (Exception ex) {
        }
    }

    private String generateBaseUrl(String location, String category, String subCategory) {
        String temp = website;
        if(!(location.equalsIgnoreCase("default")))
            temp = location + "." + website;
        if(subCategory.equals("all"))
            temp += "/d/" + category + "/search/" + getCatId(category);
        else
            temp += "/d/" + subCategory + "/search/" + getSubCatId(category, subCategory);
        
        return temp;
    }

    private String getCatId(String category) {
        switch(category){
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
        
    }

}
