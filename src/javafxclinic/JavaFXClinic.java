/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import DBAccess.ClinicDBAccess;
import java.awt.event.WindowAdapter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author vlama
 */
public class JavaFXClinic extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        ClinicDBAccess bbdd = ClinicDBAccess.getSingletonClinicDBAccess();
        //FXMLLoader dc = (FXMLLoader)root.<FXMLDocumentController>getController();
        Scene scene = new Scene(root);
        stage.setMinHeight(800);
        stage.setMinWidth(1100);
        stage.setScene(scene);
        stage.setTitle("Servicios Medicos Avanzados VV");
        try{stage.getIcons().add(new Image(getClass().getResourceAsStream("/javafxclinic/img/iconos/logo.png")));}catch(Exception e){}
        /*stage.setOnCloseRequest((WindowEvent e) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(bbdd.getClinicName());
            alert.setHeaderText("Guardando...");
            alert.setContentText("La aplicacion va salvar los datos en la BBDD. Esta acción puede llevar unos minutos...");
            alert.show();
            bbdd.saveDB();
        });*/
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
