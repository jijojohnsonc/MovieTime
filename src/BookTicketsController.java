/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author jijo
 */
public class BookTicketsController implements Initializable {

    @FXML
    private Button buttonSubmit;
    @FXML
    private ChoiceBox<Date> dateChoiceBox;
    @FXML
    private ChoiceBox<String> theatreChoiceBox;
    
    private int theatreID;
    private int show_no;
    private Date date;
    protected static int showID;
    @FXML
    private ChoiceBox<String> showChoiceBox;
    @FXML
    private ChoiceBox<String> seatsChoiceBox;
    private ArrayList<String> seatsSelected;
    @FXML
    private Label seatsLabel;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        seatsSelected = new ArrayList<String>();
        try {
            ResultSet rs = FXMain.stmt.executeQuery("select Name from THEATRES where City='"+FXMain.city+"' and ID in (select Theatre_ID from SHOWS where Movie_ID="+HomeController.movieID+")");
            while (rs.next()) {
                theatreChoiceBox.getItems().add(rs.getString("Name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void buttonProfileClicked(MouseEvent event) {
    }

    @FXML
    private void buttonSubmitClicked(MouseEvent event) {
        try {
            PreparedStatement ps = FXMain.conn.prepareStatement("insert into TICKETS(User_ID, Show_ID, Seats) values (?,?,?)");
            ps.setInt(1, FXMain.logged_userid);
            ps.setInt(2, showID);
            ps.setInt(3, seatsSelected.size());
            ps.executeUpdate();
            ResultSet rs = FXMain.stmt.executeQuery("SELECT LAST_INSERT_ID();");
            int ticketID = 0;
            if (rs.next()) {
                ticketID = rs.getInt(1);
            }
            ps = FXMain.conn.prepareStatement("update SEATS set Booked=1, Ticket_ID=? where Show_ID=? and Seat_no=?");
            ps.setInt(2, showID);
            ps.setInt(1, ticketID);
            Iterator<String> itr = seatsSelected.iterator();
            while (itr.hasNext()) {
                ps.setInt(3, Integer.parseInt(itr.next()));
                ps.executeUpdate();
            }
            ps = FXMain.conn.prepareStatement("update SHOWS set Booked_seats = Booked_seats + ? where ID=?");
            ps.setInt(1,seatsSelected.size());
            ps.setInt(2, showID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Success.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(BookTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }

    @FXML
    private void theatreButtonClicked(MouseEvent event) {
        try {
            ResultSet rs = FXMain.stmt.executeQuery("select ID from THEATRES where Name='"+theatreChoiceBox.getSelectionModel().getSelectedItem()+"';");
                if (rs.next()) {
                    theatreID = rs.getInt("ID");
                }
            rs = FXMain.stmt.executeQuery("select unique Date from SHOWS where Theatre_ID="+theatreID+";");
            while (rs.next()) {
                dateChoiceBox.getItems().add(rs.getDate("Date"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void dateButtonClicked(MouseEvent event) {
        try {
            PreparedStatement ps = FXMain.conn.prepareStatement("select Show_no from SHOWS where Movie_ID=? and Theatre_ID=? and Date=?");
            ps.setInt(1, HomeController.movieID);
            ps.setInt(2, theatreID);
            date = dateChoiceBox.getSelectionModel().getSelectedItem();
            ps.setDate(3, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showChoiceBox.getItems().add(rs.getInt("Show_no")+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void seatsButtonClicked(MouseEvent event) {
        seatsSelected.add(seatsChoiceBox.getSelectionModel().getSelectedItem());
        seatsLabel.setText(seatsSelected+"");
    }

    @FXML
    private void showButtonClicked(MouseEvent event) {
        try {
            PreparedStatement ps = FXMain.conn.prepareStatement("select ID from SHOWS where Movie_ID=? and Theatre_ID=? and Date=? and Show_no=?");
            ps.setInt(1, HomeController.movieID);
            ps.setInt(2, theatreID);
            ps.setDate(3, date);
            ps.setInt(4, Integer.parseInt(showChoiceBox.getSelectionModel().getSelectedItem()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                showID = rs.getInt("ID");
            }
            ps = FXMain.conn.prepareStatement("select Seat_no from SEATS where Show_ID=? and Booked=0");
            ps.setInt(1, showID);
            rs = ps.executeQuery();
            while (rs.next()) {
                seatsChoiceBox.getItems().add(rs.getInt("Seat_no")+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
