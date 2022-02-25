/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jijo
 */
public class LocationController implements Initializable {
    
    private ResultSet rs;

    @FXML
    private ComboBox<String> boxLocation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            rs = FXMain.stmt.executeQuery("select unique City from THEATRES order by City;");
            if (rs.next()) {
                boxLocation.getItems().add(rs.getString("City"));
                boxLocation.setValue(rs.getString("City"));
            }
            while (rs.next()) {
                boxLocation.getItems().add(rs.getString("City"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void buttonSelectClicked(MouseEvent event) {
        FXMain.city = boxLocation.getValue();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }

    
}
