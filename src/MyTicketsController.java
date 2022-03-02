/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class MyTicketsController implements Initializable {

    @FXML
    private Label theatreLabel;
    @FXML
    private Label movieLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label seatsLabel;
    
    private PreparedStatement ps;
    private ResultSet rs;
    @FXML
    private Label seatNoLabel;
    @FXML
    private Label ticketNoLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ps = FXMain.conn.prepareStatement("Select * from TICKETS where User_ID=?");
            ps.setInt(1, FXMain.logged_userid);
            rs = ps.executeQuery();
            if (rs.next()) {
                ticketNoLabel.setText("Ticket no. "+rs.getInt("ID"));
                seatNoLabel.setText("Number of seats: "+rs.getInt("Seats"));
                ResultSet rs1 = FXMain.stmt.executeQuery("Select * from SHOWS where ID="+rs.getInt("Show_ID")+";");
                if (rs1.next()) {
                    dateLabel.setText(rs1.getDate("Date")+", Show no. "+rs1.getInt("Show_no"));
                    ResultSet rs2 = FXMain.stmt.executeQuery("Select Name, Language from MOVIES where ID="+rs1.getInt("Movie_ID")+";");
                    if (rs2.next()) {
                        movieLabel.setText(rs2.getString("Name")+", "+rs2.getString("Language"));
                    }
                    rs2 = FXMain.stmt.executeQuery("Select Name, City from THEATRES where ID="+rs1.getInt("Theatre_ID")+";");
                    if (rs2.next()) {
                        theatreLabel.setText(rs2.getString("Name")+", "+rs2.getString("City"));
                    }
                    rs2 = FXMain.stmt.executeQuery("Select Seat_no from SEATS where Ticket_ID="+rs.getInt("ID"));
                    if (rs2.next()) {
                        seatsLabel.setText(rs2.getInt("Seat_no")+"");
                    }
                    while (rs2.next()) {
                        seatsLabel.setText(seatsLabel.getText()+", "+rs2.getInt("Seat_no"));
                    }
                }  
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void buttonProfileClicked(MouseEvent event) {
    }

    @FXML
    private void previousClicked(MouseEvent event) {
        try {
            if (rs.previous()) {
                seatsLabel.setText("");
                ticketNoLabel.setText("Ticket no. "+rs.getInt("ID"));
                seatNoLabel.setText("Number of seats: "+rs.getInt("Seats"));
                ResultSet rs1 = FXMain.stmt.executeQuery("Select * from SHOWS where ID="+rs.getInt("Show_ID")+";");
                if (rs1.next()) {
                    dateLabel.setText(rs1.getDate("Date")+", Show no. "+rs1.getInt("Show_no"));
                    ResultSet rs2 = FXMain.stmt.executeQuery("Select Name, Language from MOVIES where ID="+rs1.getInt("Movie_ID")+";");
                    if (rs2.next()) {
                        movieLabel.setText(rs2.getString("Name")+", "+rs2.getString("Language"));
                    }
                    rs2 = FXMain.stmt.executeQuery("Select Name, City from THEATRES where ID="+rs1.getInt("Theatre_ID")+";");
                    if (rs2.next()) {
                        theatreLabel.setText(rs2.getString("Name")+", "+rs2.getString("City"));
                    }
                    rs2 = FXMain.stmt.executeQuery("Select Seat_no from SEATS where Ticket_ID="+rs.getInt("ID"));
                    if (rs2.next()) {
                        seatsLabel.setText(rs2.getInt("Seat_no")+"");
                    }
                    while (rs2.next()) {
                        seatsLabel.setText(seatsLabel.getText()+", "+rs2.getInt("Seat_no"));
                    }
                }  
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void nextClicked(MouseEvent event) {
        try {
            if (rs.next()) {
                seatsLabel.setText("");
                ticketNoLabel.setText("Ticket no. "+rs.getInt("ID"));
                seatNoLabel.setText("Number of seats: "+rs.getInt("Seats"));
                ResultSet rs1 = FXMain.stmt.executeQuery("Select * from SHOWS where ID="+rs.getInt("Show_ID")+";");
                if (rs1.next()) {
                    dateLabel.setText(rs1.getDate("Date")+", Show no. "+rs1.getInt("Show_no"));
                    ResultSet rs2 = FXMain.stmt.executeQuery("Select Name, Language from MOVIES where ID="+rs1.getInt("Movie_ID")+";");
                    if (rs2.next()) {
                        movieLabel.setText(rs2.getString("Name")+", "+rs2.getString("Language"));
                    }
                    rs2 = FXMain.stmt.executeQuery("Select Name, City from THEATRES where ID="+rs1.getInt("Theatre_ID")+";");
                    if (rs2.next()) {
                        theatreLabel.setText(rs2.getString("Name")+", "+rs2.getString("City"));
                    }
                    rs2 = FXMain.stmt.executeQuery("Select Seat_no from SEATS where Ticket_ID="+rs.getInt("ID"));
                    if (rs2.next()) {
                        seatsLabel.setText(rs2.getInt("Seat_no")+"");
                    }
                    while (rs2.next()) {
                        seatsLabel.setText(seatsLabel.getText()+", "+rs2.getInt("Seat_no"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyTicketsController.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }

    @FXML
    private void cancelClicked(MouseEvent event) {
/*        try {
            FXMain.stmt.executeUpdate("SET foreign_key_checks = 0;");
            FXMain.stmt.executeUpdate("update SEATS set Booked=0, Ticket_ID=-1 where Ticket_ID="+rs.getInt("ID")+";");
            FXMain.stmt.executeUpdate("update SHOWS set Booked_seats = Booked_seats - "+rs.getInt("Seats")+" where ID="+rs.getInt("Show_ID")+";");
            FXMain.stmt.executeUpdate("delete from TICKETS where ID="+rs.getInt("ID")+";");
            FXMain.stmt.executeUpdate("SET foreign_key_checks = 1;");
        } catch (SQLException ex) {
            Logger.getLogger(MyTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }   */
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MyTicketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        FXMain.mainStage.setScene(scene);
    }
}
