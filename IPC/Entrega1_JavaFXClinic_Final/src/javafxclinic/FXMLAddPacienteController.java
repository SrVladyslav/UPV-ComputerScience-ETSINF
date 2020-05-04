/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import DBAccess.ClinicDBAccess;
import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static javafxclinic.FXMLDocumentController.bbdd;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author vlama
 */
public class FXMLAddPacienteController implements Initializable {

    @FXML
    private GridPane add_paciente;
    @FXML
    private TextField field_nuevo_paciente_nom;
    @FXML
    private TextField field_nuevo_paciente_nom_identificacion;
    @FXML
    private TextField field_nuevo_paciente_tel;
    @FXML
    private TextField field_nuevo_paciente_apellidos;
    @FXML
    private CheckBox chek_nuevo_paciente;
    @FXML
    private Button button_add_paciente;
    @FXML
    private Button button_cancelar_ap;
    @FXML
    private GridPane profile;
    @FXML
    private ImageView img_profile;
    
    private static Patient paciente;
    @FXML
    private GridPane col;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        img_profile.fitHeightProperty().bind(profile.heightProperty());
        img_profile.fitWidthProperty().bind(profile.widthProperty());
        //Image img = new Image((new File("img/iconos/default.png")).toURI().toString());
        //Image img1 = new Image("img/fotoLogin.png");
        img_profile.setDisable(true);
        chek_nuevo_paciente.selectedProperty().addListener(a -> {
            if(chek_nuevo_paciente.isSelected()){
                //img_profile.imageProperty().setValue(img);
                //img_profile.imageProperty().set(img);
                img_profile.setDisable(false);
            }else {
                //img_profile.imageProperty().setValue(img1);
                img_profile.setDisable(true);
            }
        });
        //button_add_paciente.lookup(".text").setScaleY(col.getHeight());
        //button_add_paciente.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button_add_paciente));
        /*button_add_paciente.layoutBoundsProperty().addListener((o,old, nev) -> {
            button_add_paciente.lookup(".text").setScaleY(button_add_paciente.getMaxHeight());//responsive del boton
        });*/
    }    
    
    /*private void scaleButton(Button button) {
        double w = button.getWidth();
        double h = button.getHeight();
        double bw = button.prefWidth(-1);
        double bh = button.prefHeight(-1);
        if (w == 0 || h == 0 || bw == 0 || bh == 0) return ;
        double hScale = w / bw ;
        double vScale = h / bw ;
        double scale = Math.min(hScale, vScale);
        button.lookup(".text").setScaleX(scale);
        button.lookup(".text").setScaleY(scale);
    }*/


    @FXML
    private void addPaciente(ActionEvent event) {
        paciente = new Patient(field_nuevo_paciente_nom_identificacion.getText(),field_nuevo_paciente_nom.getText(), field_nuevo_paciente_apellidos.getText(),
                                        field_nuevo_paciente_tel.getText(),img_profile.imageProperty().getValue());
        ClinicDBAccess bbdd = ClinicDBAccess.getSingletonClinicDBAccess();
        bbdd.getPatients().add(paciente);
        System.out.println("Añadiendo paciente");
        bbdd.saveDB();
        ((Stage)button_cancelar_ap.getScene().getWindow()).close();
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        ((Stage)button_cancelar_ap.getScene().getWindow()).close();
    }

    @FXML
    private void cargarImagen(MouseEvent event) {
        FileChooser fc = new FileChooser();
        File imagen = fc.showOpenDialog(null);
        if(fc != null){
            Image img = new Image(imagen.toURI().toString());
            img_profile.imageProperty().setValue(img);
        }
    }
    
    public Patient getPatient(){
        return paciente;
    }
    
}
