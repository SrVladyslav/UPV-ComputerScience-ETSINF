/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Days;
import model.Doctor;
import model.ExaminationRoom;

/**
 * FXML Controller class
 *
 * @author V1ct0r
 */
public class FXMLAnyadirDoctorController implements Initializable {

    @FXML
    private TextField nuevo_hora_inicio;
    @FXML
    private TextField nuevo_hora_fin;
    @FXML
    private TextField nuevo_num_sala;
    @FXML
    private ImageView img_profile;
    @FXML
    private TextField field_nuevo_doctor_nom;
    @FXML
    private TextField field_nuevo_doctor_apellidos;
    @FXML
    private TextField field_nuevo_doctor_tel;
    @FXML
    private TextField field_nuevo_doctor_num_identificacion;
    @FXML
    private Button button_add_doctor;
    @FXML
    private Button button_cancelar_ad;
    @FXML
    private CheckBox chek_nuevo_doctor;
    @FXML
    private CheckBox check_lunes;
    @FXML
    private CheckBox check_martes;
    @FXML
    private CheckBox check_miercoles;
    @FXML
    private CheckBox check_jueves;
    @FXML
    private CheckBox check_viernes;
    @FXML
    private CheckBox check_sabado;
    @FXML
    private CheckBox check_domingo;
    @FXML
    private GridPane add_doctor;
    
    private static Doctor doctor;
    private boolean pulsadoCancelar = false;
    @FXML
    private TextField nuevo_min_fin;
    @FXML
    private TextField nuevo_min_inicio;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        img_profile.setDisable(true);
        chek_nuevo_doctor.selectedProperty().addListener(a -> {
            if(chek_nuevo_doctor.isSelected()){
                //img_profile.imageProperty().setValue(img);
                //img_profile.imageProperty().set(img);
                img_profile.setDisable(false);
            }else {
                //img_profile.imageProperty().setValue(img1);
                img_profile.setDisable(true);
            }
        });
    }    


    @FXML
    private void cerrarVentana(ActionEvent event) {
        pulsadoCancelar = true;
        ((Stage)button_cancelar_ad.getScene().getWindow()).close();
    }

    @FXML
    private void addDoctor(ActionEvent event) {
        ExaminationRoom sala = new ExaminationRoom(Integer.parseInt(nuevo_num_sala.getText()),"");
       
        LocalTime inicio = LocalTime.of(Integer.parseInt(nuevo_hora_inicio.getText()),Integer.parseInt(nuevo_min_inicio.getText()));
        LocalTime fin = LocalTime.of(Integer.parseInt(nuevo_hora_fin.getText()),Integer.parseInt(nuevo_min_fin.getText()));
        String id = field_nuevo_doctor_num_identificacion.getText();
        String nombre = field_nuevo_doctor_nom.getText();
        String apellidos = field_nuevo_doctor_apellidos.getText();
        String telefono = field_nuevo_doctor_tel.getText();
        Image profile = img_profile.imageProperty().getValue();
        
        doctor = new Doctor(sala,null,inicio,fin,id,nombre,apellidos,telefono,profile);
        ArrayList<Days> dias = new ArrayList<Days>();
        if(check_lunes.isSelected()){dias.add(Days.Monday);}
        else if(check_martes.isSelected()){dias.add(Days.Tuesday);}
        else if(check_miercoles.isSelected()){dias.add(Days.Wednesday);}
        else if(check_jueves.isSelected()){dias.add(Days.Thursday);}
        else if(check_viernes.isSelected()){dias.add(Days.Friday);}
        else if(check_sabado.isSelected()){dias.add(Days.Saturday);}
        else if(check_domingo.isSelected()){dias.add(Days.Sunday);}
        doctor.setVisitDays(dias);
        ((Stage)button_cancelar_ad.getScene().getWindow()).close();
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
    public boolean getCancelar(){
        return pulsadoCancelar;
    }
    
    public Doctor getDoctor(){
        return doctor;
    }
}