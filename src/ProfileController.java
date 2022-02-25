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
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jijo
 */
public class ProfileController implements Initializable {

    @FXML
    private Label labelName;
    @FXML
    private Label labelEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ResultSet rs = FXMain.stmt.executeQuery("select Name, Email from USERS where id="+FXMain.logged_userid);
            if (rs.next()) {
                labelName.setText(rs.getString(1));
                labelEmail.setText(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    


    @FXML
    private void buttonTicketsClicked(MouseEvent event) {
    }

    @FXML
    private void buttonLogoutClicked(MouseEvent event) {
        FXMain.logged_userid = 0;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
        
    }

    @FXML
    private void labelBackClicked(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }

    @FXML
    private void labelEditClicked(MouseEvent event) {
        
    }
    
}
