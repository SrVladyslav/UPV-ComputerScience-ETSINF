/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vlama
 */
public class FXMLConfirmacionController implements Initializable {

    @FXML
    private GridPane panel;
    @FXML
    private ImageView img;
    @FXML
    private Button botoncito;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        new Thread(()->{
            try{
                Thread.sleep(2000);
                System.out.println("Duermete niño");
            }catch(Exception e){System.out.print(e);    
        }
        }).start();
    }    

    @FXML
    private void initialize(MouseEvent event) {
        ((Stage)img.getScene().getWindow()).close();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        ((Stage)botoncito.getScene().getWindow()).close();
    }
    
}
