package scraper;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FxmlUiController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label titleLbl;

    @FXML
    private Button searchBtn;

    @FXML
    private Label categoryLbl;

    @FXML
    private Label subcategoryLbl;

    @FXML
    private TextField locTextfield;

    @FXML
    private TextField rangeTextfield;

    @FXML
    private TextField zipTextField;

    @FXML
    private ChoiceBox<String> categoryChoice;

    @FXML
    private ChoiceBox<String> subcategoryChoice;

    @FXML
    private ComboBox<?> comboChoice;

    ObservableList<String> subOptions
            = FXCollections.observableArrayList();

    //The only option for resumes category
    String[] dfltOptions = {"all"};

    String[] commOptions = {"all", "activities", "artists", "childcare", "classes",
        "events", "general", "groups", "local news", "lost+found",
        "missed connections", "musicians", "pets", "politics",
        "rants & raves", "rideshare", "volunteers"};

    String[] servOptions = {"all", "automotive", "beauty", "cell/mobile", "computer",
        "creative", "cycle", "event", "farm+garden", "financial", "household",
        "labor/move", "legal", "lessons", "marine", "pet", "real estate",
        "skilled trade", "sm biz ads", "travel/vac", "write/ed/tran"};

    String[] housOptions = {"all", "apts / housing", "housing swap", "housing wanted",
        "office / commercial", "parking / storage", "real estate for sale",
        "rooms / shared", "rooms wanted", "sublets / temporary",
        "vacation rentals"};

    String[] saleOptions = {"all", "antiques", "appliances", "arts+crafts",
        "atv/utv/sno", "auto parts", "aviation", "baby+kid", "barter",
        "beauty+hlth", "bike parts", "bikes", "boat parts", "boats", "books",
        "business", "cars+trucks", "cds/dvd/vhs", "cell phones", "clothes+acc",
        "collectibles", "computer parts", "computers", "electronics",
        "farm+garden", "free", "furniture", "garage sale", "general",
        "heavy equip", "household", "jewelry", "materials", "motorcycle parts",
        "motorcycles", "music instr", "photo+video", "rvs+camp", "sporting",
        "tickets", "tools", "toys+games", "trailers", "video gaming", "wanted",
        "wheels+tires"};

    String[] jobsOptions = {"all", "accounting+finance", "admin / office",
        "arch / engineering", "art / media / design", "biotech / science",
        "business / mgmt", "customer service", "education", "etc / misc",
        "food / bev / hosp", "general labor", "government", "human resources",
        "legal / paralegal", "manufacturing", "marketing / pr / ad",
        "medical / health", "nonprofit sector", "real estate",
        "retail / wholesale", "sales / biz dev", "salon / spa / fitness",
        "security", "skilled trade / craft", "software / qa / dba",
        "systems / network", "technical support", "transport",
        "tv / film / video", "web / info design", "writing / editing"};

    String[] gigsOptions = {"all", "computer", "creative", "crew", "domestic",
        "event", "labor", "talent", "writing"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOptions();
    }
    
    @FXML
    private void searchBtn(ActionEvent event) {
        //Creates searchQuery object from menu
        System.out.println("Clicked Search");
        //Make sure necessary fields are populated before processing
        //check valid range and zipcode
        
        searchQuery newSearch = new searchQuery(getCategory(), getSubcategory());
        
        System.out.println(newSearch.toString());
                
        //open other fxml file with information();
        
    }

    private void loadOptions() {
        //hardcoded initial saleOptions
        populateList(subOptions, saleOptions);
        updateChoiceBox(subcategoryChoice, subOptions);

        categoryChoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {

                    switch (new_val) {
                        case "community":
                            populateList(subOptions, commOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "services":
                            populateList(subOptions, servOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "housing":
                            populateList(subOptions, housOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "sale/wanted":
                            populateList(subOptions, saleOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "jobs":
                            populateList(subOptions, jobsOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "gigs":
                            populateList(subOptions, gigsOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        default:
                            populateList(subOptions, dfltOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);

                    }
                });
    }

    private void populateList(ObservableList<String> optionList, String[] viableOptions) {
        optionList.clear();
        optionList.addAll(Arrays.asList(viableOptions));

    }

    private void updateChoiceBox(ChoiceBox<String> cb, ObservableList<String> optionList) {
        cb.setItems(optionList);
        cb.setValue(optionList.get(0));
    }
    
    private String getCategory() {
        return categoryChoice.getValue();
    }
    
    private String getSubcategory() {
        return subcategoryChoice.getValue();
    }

}
