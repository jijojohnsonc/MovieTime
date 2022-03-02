/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jijo
 */
public class HomeController implements Initializable {

    protected static String movie;
    protected static int movieID;
    private ResultSet rs; 
    
    @FXML
    private ListView<String> listMovies;
    @FXML
    private Label labelCity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelCity.setText(FXMain.city+" >");
        try {
            rs = FXMain.stmt.executeQuery("select ID, Name from MOVIES where ID in (select unique Movie_ID from SHOWS where Theatre_ID in (select ID from THEATRES where city='"+FXMain.city+"'));");
            while (rs.next()) {
                listMovies.getItems().add(rs.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void listClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                ResultSet rs = FXMain.stmt.executeQuery("select ID from MOVIES where Name='"+listMovies.getSelectionModel().getSelectedItem()+"';");
                if (rs.next()) {
                    movieID = rs.getInt("ID");
                }
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            movie = listMovies.getSelectionModel().getSelectedItem();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("Movie.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            FXMain.mainStage.setScene(scene);
        }
    }

    @FXML
    private void buttonProfileClicked(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }
    
}
