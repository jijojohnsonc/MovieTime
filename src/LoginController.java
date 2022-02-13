/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jijo
 */
public class LoginController implements Initializable {

    @FXML
    private Button buttonSubmit;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    @FXML
    private Label labelError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonSubmitClicked(MouseEvent event) {
        try {
            ResultSet rs = FXMain.stmt.executeQuery("select ID, Password from USERS where Email='"+textUsername.getText()+"';");
            if(rs.next()){
                if (textPassword.getText().equals(rs.getString("Password"))) {
                    labelError.setVisible(false);
                    FXMain.logged_userid = rs.getInt("ID");
                } else {
                    labelError.setText("Incorrect Password!");
                    labelError.setVisible(true);
                }
            } else {
                labelError.setText("User not found!");
                labelError.setVisible(true);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void labelErrorClicked(MouseEvent event) {
        labelError.setVisible(false);
    }
    
}
