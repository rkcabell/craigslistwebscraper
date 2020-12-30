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

    private void scrape(String category, String subCategory, String location, int range, int zip) {

        /* location formatting must be as follows:
      * 'location' followed by a period (.) */
        String currentLocation = "raleigh.";
        String alternateLocation = "losangeles.";
        String errorLocation = "";

        //possible sections
        String freeSection = "d/free-stuff/search/zip";
        String carSection = "d/cars-trucks/search/cta";
        String computerPartsSection = "d/computer-parts/search/syp";

        System.out.println("Printing los angeles computer parts page");
        showInfo(alternateLocation, computerPartsSection);
        System.out.println("Printing raleigh with no section");
        showInfo(currentLocation, errorLocation);
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

}
