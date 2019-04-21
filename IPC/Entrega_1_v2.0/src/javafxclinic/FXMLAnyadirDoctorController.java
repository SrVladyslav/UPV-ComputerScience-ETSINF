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
import java.util.regex.Pattern;
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
        try{
            ExaminationRoom sala = new ExaminationRoom(Integer.parseInt(nuevo_num_sala.getText()),"");
            boolean correcto = true;
            LocalTime inicio = LocalTime.of(Integer.parseInt(nuevo_hora_inicio.getText()),Integer.parseInt(nuevo_min_inicio.getText()));
            LocalTime fin = LocalTime.of(Integer.parseInt(nuevo_hora_fin.getText()),Integer.parseInt(nuevo_min_fin.getText()));
            String id = field_nuevo_doctor_num_identificacion.getText();
            String nombre = field_nuevo_doctor_nom.getText();
            String apellidos = field_nuevo_doctor_apellidos.getText();
            String telefono = field_nuevo_doctor_tel.getText();
            Image profile = img_profile.imageProperty().getValue();

            if(!Pattern.matches("\\d{8}[A-Z]", field_nuevo_doctor_num_identificacion.getText())){
                correcto = false;
                field_nuevo_doctor_num_identificacion.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("[A-Z][a-z]*", field_nuevo_doctor_nom.getText())){
                correcto = false;
                field_nuevo_doctor_nom.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("[A-Z][a-z]* *[A-Z]*[a-z]*", field_nuevo_doctor_apellidos.getText())){
                correcto = false;
                field_nuevo_doctor_apellidos.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("\\d{9}", field_nuevo_doctor_tel.getText())){
                correcto = false;
                field_nuevo_doctor_tel.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("[0-9]+", nuevo_num_sala.getText())){
                correcto = false;
                nuevo_num_sala.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("\\d{2}", nuevo_hora_inicio.getText()) || !Pattern.matches("\\d{2}", nuevo_min_inicio.getText())){
                correcto = false;
                nuevo_hora_inicio.setStyle("-fx-background-color:#E25E5E;");
                nuevo_min_inicio.setStyle("-fx-background-color:#E25E5E;");
            }
            if(!Pattern.matches("\\d{2}", nuevo_hora_fin.getText()) || !Pattern.matches("\\d{2}", nuevo_min_fin.getText())){
                correcto = false;
                nuevo_hora_fin.setStyle("-fx-background-color:#E25E5E;");
                nuevo_min_fin.setStyle("-fx-background-color:#E25E5E;");
            }
            doctor = new Doctor(sala,null,inicio,fin,id,nombre,apellidos,telefono,profile);
            ArrayList<Days> dias = new ArrayList<Days>();
            if(check_lunes.isSelected()){dias.add(Days.Monday);}
            if(check_martes.isSelected()){dias.add(Days.Tuesday);}
            if(check_miercoles.isSelected()){dias.add(Days.Wednesday);}
            if(check_jueves.isSelected()){dias.add(Days.Thursday);}
            if(check_viernes.isSelected()){dias.add(Days.Friday);}
            if(check_sabado.isSelected()){dias.add(Days.Saturday);}
            if(check_domingo.isSelected()){dias.add(Days.Sunday);}
            doctor.setVisitDays(dias);

            if(correcto){
                ((Stage)button_cancelar_ad.getScene().getWindow()).close();
            }
        }catch(Exception e){}
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
        }catch(Exception e){ System.err.println("UPS");}
    }
    public boolean getCancelar(){
        return pulsadoCancelar;
    }
    
    public Doctor getDoctor(){
        return doctor;
    }
}