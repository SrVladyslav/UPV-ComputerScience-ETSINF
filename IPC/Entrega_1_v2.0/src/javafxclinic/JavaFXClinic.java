/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import DBAccess.ClinicDBAccess;
import java.awt.event.WindowAdapter;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
        //FXMLDocumentController dc = (FXMLDocumentController)root.<FXMLDocumentController>getController();
        Scene scene = new Scene(root);
        stage.setMinHeight(800);
        stage.setMinWidth(1100);
        stage.setScene(scene);
        stage.setTitle("Servicios Medicos Avanzados VV");
        try{stage.getIcons().add(new Image(getClass().getResourceAsStream("/javafxclinic/img/iconos/logo.png")));}catch(Exception e){}
        stage.setOnCloseRequest((WindowEvent e) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(bbdd.getClinicName());
            alert.setHeaderText("Atención...");
            alert.setContentText("Va salir de la aplicación sin guardar los cambios!");
            //alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                stage.close();
            }else{
                e.consume();//no cierra la ventana, aborta mision
            }
            //bbdd.saveDB();
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
