/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author vlama
 */
public class FXMLMostrarInfoPController implements Initializable {

    @FXML
    private ImageView profile_img;
    @FXML
    private AnchorPane panel_img;
    @FXML
    private GridPane panel_informacion;
    @FXML
    private Label l_nombre;
    @FXML
    private Label l_apellidos;
    @FXML
    private Label l_tel;
    @FXML
    private Label l_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        profile_img.fitHeightProperty().bind(panel_img.heightProperty());
        profile_img.fitWidthProperty().bind(panel_img.widthProperty());
    }    
    
    public void rellenaPanel(Patient paciente){
        String nombre = paciente.getName();
        String apellidos = paciente .getSurname();
        String id = paciente.getIdentifier();
        String telefono = paciente.getTelephon();
        Image img = paciente.getPhoto();
        
        //declaraciones
        profile_img.imageProperty().setValue(img);
        l_nombre.setText(nombre);
        l_apellidos.setText(apellidos);
        l_tel.setText(telefono);
        l_id.setText(id);
    }
    
}
