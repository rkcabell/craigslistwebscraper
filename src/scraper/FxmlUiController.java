package scraper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    
    List<Map<String, String>> options = searchQuery.getOptions();
    /**
     * option index is in alphabetical order
        options.add(dfltOptions); - 0
        options.add(commOptions); - 1
        options.add(gigsOptions); - 2
        options.add(housOptions); - 3
        options.add(jobsOptions); - 4
        options.add(saleOptions); - 5
        options.add(servOptions); - 6
        
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOptions();
    }
    
    @FXML
    private void searchClick(ActionEvent event) throws MalformedURLException {
        //Creates searchQuery object from menu
        System.out.println("Clicked Search");
        
        if(!requiredFieldsFilled()){
            throw new IllegalArgumentException();
        }
        
        /*
        //Make sure necessary fields are populated before processing
        //Figure out which contructor to use before calling
        //check valid range and zipcode if it has one
        */
        searchQuery newSearch = new searchQuery(getCategory(), getSubcategory(), getLocation());
        
        System.out.println("Search is: " + newSearch.toString());
        
        Scraper scraper = new Scraper();
        Item[] listings = scraper.scrape(newSearch);
        
        //display in other fxml
        //temporary display foreach loop
        for (Item item : listings) {
                System.out.println(item.toString());
            }
        
    }
    private void loadOptions() {
        //hardcoded initial saleOptions
        populateList(subOptions, options.get(5));
        updateChoiceBox(subcategoryChoice, subOptions);

        categoryChoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    switch (new_val) {
                        case "community":
                            populateList(subOptions, options.get(1));
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "services":
                            populateList(subOptions, options.get(6));
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "housing":
                            populateList(subOptions, options.get(3));
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "sale/wanted":
                            populateList(subOptions, options.get(5));
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "jobs":
                            populateList(subOptions, options.get(4));
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        case "gigs":
                            populateList(subOptions, options.get(2));
                            updateChoiceBox(subcategoryChoice, subOptions);
                            break;
                        default:
                            //used for resumes (only has "all" option)
                            populateList(subOptions, options.get(0));
                            updateChoiceBox(subcategoryChoice, subOptions);

                    }
                });
    }

    private void populateList(ObservableList<String> optionList, Map<String, String> viableOptions) {
        optionList.clear();
        viableOptions.entrySet().forEach(entry -> {
            optionList.add(entry.getKey());
        });
        Collections.sort(optionList);

    }

    private void updateChoiceBox(ChoiceBox<String> cb, ObservableList<String> optionList) {
        cb.setItems(optionList);
        cb.setValue(optionList.get(0));
    }
    
    private String getCategory() {
        //for-sale is the only category that isnt the same as its display
        String cat = categoryChoice.getValue();
        if (cat.equals("sale/wanted"))
            return "for-sale";
        return cat;
    }
    
    private String getSubcategory() {
        //find subcat in options
        String subcat = subcategoryChoice.getValue();
        for (int i = 0 ; i < options.size() ; i++) {
            Map<String, String> myMap = options.get(i);
            System.out.println("Data For Map" + i);
            for (Entry<String, String> entrySet : myMap.entrySet()) {
                if(subcat.equals(entrySet.getKey()));
                    return entrySet.getValue();
            }
        }
        return null;
    }
    
    private String getLocation() {
        return locTextfield.getText();
    }

    private boolean requiredFieldsFilled() {
        //check range and zip
        
        return !locTextfield.getText().isEmpty();
    }

}
