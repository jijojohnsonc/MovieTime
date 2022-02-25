/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

/**
 *
 * @author jijo
 */
public class FXMain extends Application {
    
    protected static Connection conn;
    protected static Statement stmt;
    protected static Parent root;
    protected static Stage mainStage;
    protected static int logged_userid = 0;
    protected static String city;
    
    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        try {
            root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
            
            Scene scene = new Scene(root);
            
            mainStage.setTitle("Movie Time");
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        conn = null;
        stmt = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/DBMovieS",
                    "DBMS", "Admin@123"
            );
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        launch(args);
        System.out.println(HomeController.movie);
    }
    
}