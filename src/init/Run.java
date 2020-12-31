/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scraper.FxmlUiController;

/**
 *
 * @author cabel
 */
public class Run extends Application {
    
    private static AnchorPane mainLayout;


    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Craigslist web scraper");
        FXMLLoader loader = new FXMLLoader();
        try {
            String fxmlDocPath = "./src/layout/userInterface.fxml";
            String stylesheetPath = "/layout/stylesheet.css";
            
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            loader.setLocation(FxmlUiController.class.getResource("./layout/userInterface.fxml"));
            mainLayout = loader.load(fxmlStream);
            Scene scene = new Scene(mainLayout, 600, 300);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource(stylesheetPath).toExternalForm());
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
