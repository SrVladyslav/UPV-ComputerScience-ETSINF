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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Appointment;
import model.Doctor;

/**
 * FXML Controller class
 *
 * @author vlama
 */
public class FXMLMostrarInfoCitaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private GridPane verCitaPanelFoto;
    @FXML
    private ImageView fotoCitasDoctor;
    @FXML
    private Label label_nom_doctor_cita;
    @FXML
    private Label label_apellidos_doctor_cita;
    @FXML
    private Label label_fecha_cita;
    @FXML
    private Label label_nombre_paciente;
    @FXML
    private Label label_apellidos_paciente;
    @FXML
    private Label label_sala_consulta;
    @FXML
    private Label telefono_doctor;
    @FXML
    private Label id_doctor;
    @FXML
    private Label telefono_paciente;
    @FXML
    private Label id_paciente;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fotoCitasDoctor.fitHeightProperty().bind(verCitaPanelFoto.heightProperty());
        fotoCitasDoctor.fitWidthProperty().bind(verCitaPanelFoto.widthProperty());
    }    
    
    public void setWindow(Appointment cita){
        label_nom_doctor_cita.setText(cita.getDoctor().getName());
        fotoCitasDoctor.setImage(cita.getDoctor().getPhoto());
        label_apellidos_doctor_cita.setText(cita.getDoctor().getSurname());
        label_fecha_cita.setText(toSpanish(cita.getAppointmentDay().name()));
        label_nombre_paciente.setText(cita.getPatient().getName());
        label_apellidos_paciente.setText(cita.getPatient().getSurname());
        telefono_doctor.setText(cita.getDoctor().getTelephon());
        id_doctor.setText(cita.getDoctor().getIdentifier());
        telefono_paciente.setText(cita.getPatient().getTelephon());
        id_paciente.setText(cita.getPatient().getIdentifier());
        label_sala_consulta.setText(cita.getDoctor().getExaminationRoom().getIdentNumber()+"    ");
        
    }
    
    public String toSpanish(String str){
        String res = "";
        switch(str){
            case "Monday": res = "Lunes";break;
            case "Thusday": res = "Jueves";break;
            case "Tuesday": res = "Martes";break;
            case "Wednesday": res = "Miercoles";break;
            case "Friday": res = "Viernes";break;
            case "Saturday": res = "Sabado";break;
            case "Sunday": res = "Domingo";break;
        }
        return res;
    }
}
