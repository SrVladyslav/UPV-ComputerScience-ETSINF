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
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private boolean cancelar = false;
    private double x =0, y = 0;

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
    
    @FXML
    private void drag(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
    @FXML
    private void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }


    @FXML
    private void addPaciente(ActionEvent event) {
        try{
            boolean correcto = true;
            paciente = new Patient(field_nuevo_paciente_nom_identificacion.getText(),field_nuevo_paciente_nom.getText(), field_nuevo_paciente_apellidos.getText(),
                                            field_nuevo_paciente_tel.getText(),img_profile.imageProperty().getValue());
            if(!Pattern.matches("\\d{8}[A-Z]", field_nuevo_paciente_nom_identificacion.getText())){
                correcto = false;
                field_nuevo_paciente_nom_identificacion.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("[A-Z][a-z]+", field_nuevo_paciente_nom.getText())){
                correcto = false;
                field_nuevo_paciente_nom.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("[A-Z][a-z]+ *[A-Z]*[a-z]*", field_nuevo_paciente_apellidos.getText())){
                correcto = false;
                field_nuevo_paciente_apellidos.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("\\d{9}", field_nuevo_paciente_tel.getText())){
                correcto = false;
                field_nuevo_paciente_tel.setStyle("-fx-background-color:#E25E5E;");
            }

            if(correcto){
                ((Stage)button_cancelar_ap.getScene().getWindow()).close();
            }
        }catch(Exception e){System.out.print("UPS");}
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        cancelar = true;
        ((Stage)button_cancelar_ap.getScene().getWindow()).close();
    }

    @FXML
    private void cargarImagen(MouseEvent event) {
        try{
            FileChooser fc = new FileChooser();
            File imagen = fc.showOpenDialog(null);
            if(fc != null){
                Image img = new Image(imagen.toURI().toString());
                img_profile.imageProperty().setValue(img);
            }
        }catch(Exception e){System.err.println("Error en la carga de la imagen...");}  
    }
    
    public Patient getPatient(){
        return paciente;
    }
    
    public boolean getCancelar(){
        return cancelar;
    }
}
