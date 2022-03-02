/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author jijo
 */
public class MovieController implements Initializable {

    private static ResultSet rs;
    
    @FXML
    private Label labelMovie;
    @FXML
    private Label labelDescription;
    @FXML
    private Label labelRating;
    @FXML
    private ImageView image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelMovie.setText(HomeController.movie);
        try {
            rs = FXMain.stmt.executeQuery("select * from MOVIES where ID="+HomeController.movieID+";");
            if (rs.next()) {
              labelDescription.setText(rs.getString("Description"));
              ResultSet rs2 = FXMain.stmt.executeQuery("select avg(rating) from REVIEWS where Movie_ID="+rs.getInt("ID")+";");
              if (rs2.next()) {
                  labelRating.setText(rs2.getInt(1)+" ★");
                  image.setImage(new Image(new FileInputStream("resources/images/"+rs.getInt("ID")+".png")));
              }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void buttonProfileClicked(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }

    @FXML
    private void labelReviewsClicked(MouseEvent event) {
    }

    @FXML
    private void buttonBookClicked(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("BookTickets.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }
    
}
