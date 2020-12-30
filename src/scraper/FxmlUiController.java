package scraper;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FxmlUiController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    
    String[] jobsOptions = {"all"};
    
    String[] gigsOptions = {"all", "computer", "creative", "crew", "domestic", "event",
        "labor", "talent", "writing"};
    
    String[] resuOptions = {"all"};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOptions();
    }
    
    private void loadOptions(){
        //hardcoded initial saleOptions
        populateList(subOptions, saleOptions);
        updateChoiceBox(subcategoryChoice, subOptions);
        
        categoryChoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    
                    switch(new_val){
                        case "gigs":
                            populateList(subOptions, gigsOptions);
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "sale/wanted":
                            populateList(subOptions, saleOptions);
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

}
