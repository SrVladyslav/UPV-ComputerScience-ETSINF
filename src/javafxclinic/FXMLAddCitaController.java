/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import DBAccess.ClinicDBAccess;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Appointment;
import model.Days;
import model.Doctor;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author vlama
 */
public class FXMLAddCitaController implements Initializable {

    @FXML
    private ChoiceBox<String> cb_pacientes;
    @FXML
    private ChoiceBox<String> cb_doctores;
    
    private Appointment ap ;
    private ClinicDBAccess bbdd;
    @FXML
    private GridPane calendario;
    private DatePicker datePicker;
    private boolean cargado = false;
    private DatePickerSkin datePickerSkin;
    private Node calendario_picker;
    @FXML
    private Label label_hora_ini;
    @FXML
    private Label label_hora_fin;
    @FXML
    private TextField hora;
    @FXML
    private TextField minutos;
    private Patient p;
    private Doctor d;
    private boolean guardar = false;
    @FXML
    private Button crearCitaButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //hago responsive el calendario
        calendario.vgapProperty().addListener(a->{
            datePicker.setMaxHeight(calendario.getHeight());
        });
        bbdd =  ClinicDBAccess.getSingletonClinicDBAccess();
        ArrayList<String> pacientes_cb = new ArrayList<>();
        ArrayList<String> doctores = new ArrayList<>();
        //relleno la lista de pacientes del choice box
        for(int i = 0; i < bbdd.getPatients().size(); i ++){
            pacientes_cb.add(bbdd.getPatients().get(i).getName() + " " + bbdd.getPatients().get(i).getSurname());
        }
        //relleno la lista de doctores de choice box
        for(int j = 0; j < bbdd.getDoctors().size(); j ++){
            doctores.add(bbdd.getDoctors().get(j).getName() + " " + bbdd.getDoctors().get(j).getSurname());
        }
        
        //detecto al doctor para cargar su informacion en el calendario
        cb_pacientes.setItems(FXCollections.observableList(pacientes_cb));
        cb_doctores.setItems(FXCollections.observableList(doctores));
        cb_doctores.valueProperty().addListener((o, old, nev)->{
            cargado = true;
            /*if(cargado){
                datePicker.setValue(null);
            }*/
             //Creamos el date picker incrustado
            datePicker = new DatePicker(LocalDate.now());
            datePicker.setShowWeekNumbers(true);
            datePicker.setDayCellFactory(cel-> new DiaCelda());

            datePickerSkin = new DatePickerSkin(datePicker);
            calendario_picker = datePickerSkin.getPopupContent();
            calendario.add(calendario_picker, 0, 0);
            label_hora_ini.setText(""+bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getVisitStartTime());
            label_hora_fin.setText(""+bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getVisitEndTime());
        });
    }    

    @FXML
    private void crearCita(ActionEvent event) {
        LocalDate fecha = LocalDate.of(datePicker.valueProperty().getValue().getYear(),datePicker.valueProperty().getValue().getMonth().getValue(),datePicker.valueProperty().getValue().getDayOfMonth());
        LocalTime tiempo = LocalTime.of(Integer.parseInt(hora.getText()),Integer.parseInt(minutos.getText()));
        LocalDateTime ldt = LocalDateTime.of(fecha, tiempo);
        System.out.print(bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getVisitStartTime().getHour());
        //ldt.of(fecha, tiempo);
        p = bbdd.getPatients().get(cb_pacientes.getSelectionModel().getSelectedIndex());
        d = bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex());
        ap = new Appointment(ldt,d,p);
        //comprobacion de si existe la cita
        if(true){//tiempo.getHour() >= bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getVisitStartTime().getHour()
                   // && tiempo.getHour() <= bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getVisitEndTime().getHour()){
           
            



                                        //ACABAR LA COMPROBACION
            
            
            
            
            
            
            for(int i = 0; i < bbdd.getPatientAppointments(p.getIdentifier()).size(); i ++){
                if(!bbdd.getPatientAppointments(p.getIdentifier()).get(i).equals(ap)){
                    //((Stage)crearCitaButton.getScene().getWindow()).close();
                    guardar = true;
                    System.err.println("----------CORRECTO----------");
                }else {
                    //hora.setStyle("-fx-background-color:#E25E5E;");
                    System.err.println("Error con el tiempo");
                }
            }    
        }else {
            guardar = false;
            hora.setStyle("-fx-background-color:#E25E5E;");
            minutos.setStyle("-fx-background-color:#E25E5E;");
        }
        //ArrayList<Appointment> app = 
        /*bbdd.getPatientAppointments(p.getIdentifier()).add(ap);
        //app.add(ap);
        System.err.println(ap.toString());
        bbdd.saveDB();*/
    }
    public boolean guardar(){
        return guardar;
    }
    public Appointment getAppointment(){
        return ap;
    }
    public Patient getPaciente(){
        return p;
    }
    public Doctor getMedico(){
        return d;
    }
     
    
    class DiaCelda extends DateCell {
        String newline = System.getProperty("line.separator");
        ArrayList<Days> days;  
        
        public DiaCelda(){
            days = bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getVisitDays();
        }
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            boolean cambiado = false;
            if (days.contains(Days.Monday) && item.getDayOfWeek() == DayOfWeek.MONDAY) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if (days.contains(Days.Thursday) && item.getDayOfWeek() == DayOfWeek.THURSDAY) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if (days.contains(Days.Sunday) && item.getDayOfWeek() == DayOfWeek.SUNDAY ) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if (days.contains(Days.Wednesday) && item.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if (days.contains(Days.Tuesday) && item.getDayOfWeek() == DayOfWeek.TUESDAY) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if (days.contains(Days.Friday) && item.getDayOfWeek() == DayOfWeek.FRIDAY) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if (days.contains(Days.Saturday) && item.getDayOfWeek() == DayOfWeek.SATURDAY) {
                this.setTextFill(Color.GREEN);
                this.setText(this.getText()+"\rlibre");
                cambiado = true;
                //this.setBackground("#A53737");
            }
            if(!cambiado){
                this.setTextFill(Color.RED);
                this.setDisable(true);
                this.setText(this.getText()+"\rNo trabaja");
            }
            
            if (item.isBefore(LocalDate.now())){//datePicker.getValue().plusDays(1))){
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
            }  
            
        }
    }
}
