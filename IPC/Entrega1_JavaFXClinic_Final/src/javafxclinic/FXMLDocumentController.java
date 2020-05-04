/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxclinic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Patient;
import DBAccess.ClinicDBAccess;
import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Appointment;
import model.Days;
import model.Doctor;
/**
 *
 * @author vlama
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button button_pacientes;
    @FXML
    private Button button_citas;
    @FXML
    private Button button_medicos;
    @FXML
    private Button button_salir;
    @FXML
    private GridPane panel_home;
    @FXML
    private GridPane panel_pacientes;
    @FXML
    private ImageView button_home;
    @FXML
    private GridPane panel_cabecera;
    @FXML
    private Button button_go_medicos;
    @FXML
    private Button button_go_citas;
    @FXML
    private Button button_go_pacientes;
    @FXML
    private Button button_add_n_paciente;
    @FXML
    private Button button_panel_pacientes_buscar;
    private ImageView img_profile;
    private AnchorPane profile;
    @FXML
    protected ListView<Patient> lista_pacientes;
    protected ArrayList<Patient> lista_pacientes_a;
    protected ObservableList<Patient> lista_pacientes_o;
    protected static ClinicDBAccess bbdd;
    @FXML
    private Button button_eliminar_paciente;
    @FXML
    private Button button_ver_datos_paciente;
    @FXML
    private TextField field_buscador;
    @FXML
    private ImageView main_fondo_img;
    @FXML
    private GridPane panel_citas;
    @FXML
    private ChoiceBox<String> cb_pacientes;
    @FXML
    private ChoiceBox<String> cb_doctores;
    @FXML
    private TableView<Patient> tabla_citas;
    @FXML
    private TableColumn<Doctor, String> tabla_miercoles;
    @FXML
    private TableColumn<Doctor, String> tabla_lunes;
    @FXML
    private TableColumn<Doctor, String> tabla_martes;
    @FXML
    private TableColumn<Doctor, ?> tabla_horas;
    @FXML
    private TableColumn<Patient, String> tabla_jueves;
    @FXML
    private TableColumn<?, ?> tabla_viernes;
    @FXML
    private TableColumn<?, ?> tabla_sabado;
    @FXML
    private TableColumn<?, ?> tabla_domingo;
    @FXML
    private Button button_panel_doctores_buscar1;
    @FXML
    private TextField field_buscador1;
    @FXML
    private ListView<Doctor> lista_doctores;
    private ArrayList<Doctor> lista_doctores_a;
    private ObservableList<Doctor> lista_doctores_o;
    @FXML
    private Button button_eliminar_doctor;
    @FXML
    private GridPane panel_medicos;
    @FXML
    private GridPane profile_medico;
    @FXML
    private ImageView profile_m;
    @FXML
    private Button button_ver_datos_medico;
    @FXML
    private GridPane panel_ver_medico;
    @FXML
    private Label label_nombre_medico;
    @FXML
    private Label label_apellido_medico;
    @FXML
    private Label label_id_medico;
    @FXML
    private Label label_tel_medico;
    @FXML
    private Label label_horainicio_medico;
    @FXML
    private Label label_horafin_medico;
    @FXML
    private Label label_sala_medico;
    @FXML
    private Button button_add_n_doctor;
    
    @FXML
    private void handleButtonAction(ActionEvent event){
       String id = ((Node)event.getSource()).getId();
       if(id.equals("button_pacientes")|| id.equals("button_go_pacientes")){
           panel_home.setVisible(false);
           panel_citas.setVisible(false);
           panel_medicos.setVisible(false);
           panel_pacientes.setVisible(true);
           panel_cabecera.setVisible(true);
       }else if(id.equals("button_add_n_paciente")){ //boton de añadir los nuevos pacientes
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAddPaciente.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLAddPacienteController controlador = fxmlLoader.<FXMLAddPacienteController>getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Añadir paciente");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if(!stage.isShowing()){
                    System.out.println("UPS");
                    lista_pacientes_a.add(controlador.getPatient());//bbdd.getPatients().get(lista_pacientes_a.size()));
                    lista_pacientes_o = FXCollections.observableArrayList(lista_pacientes_a);//porque tengo que hacer eso y no repinta solo?
                    lista_pacientes.setItems(lista_pacientes_o);
                }
                reloadList();
            }catch(Exception o){System.err.print(o.toString());}
        }else if(id.equals("button_eliminar_paciente")){ //boton de eliminar al paciente
            int i = lista_pacientes.getSelectionModel().getSelectedIndex();
            if(i > 0){//comprobar si esta seleccionada
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Eliminar paciente");
                alert.setHeaderText("Atención!");
                alert.setContentText("¿Seguro que quieres eliminar al paciente?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    lista_pacientes_a.remove(lista_pacientes.getSelectionModel().getSelectedItem());
                    lista_pacientes_o = FXCollections.observableArrayList(lista_pacientes_a);//porque tengo que hacer eso y no repinta solo?
                    lista_pacientes.setItems(lista_pacientes_o);
                    //bbdd.saveDB();
                    System.out.println(lista_pacientes.toString());
                } else {
                    System.out.println("CANCEL");
                }
            }
        }else if((id.equals("button_panel_pacientes_buscar")|| id.equals("field_buscador"))){
            //if(!field_buscador.getText().trim().equals("") || field_buscador.getText().trim() != null){
            if(!field_buscador.getText().isEmpty()){
                filtrar(field_buscador.getText());
                //filtrarPaciente(field_buscador.getText(), bbdd.getPatients());
            }else{
               // reloadList();
               System.err.println("Error 1");
                lista_pacientes_o = FXCollections.observableArrayList(bbdd.getPatients());//porque tengo que hacer eso y no repinta solo?
                lista_pacientes.setItems(lista_pacientes_o);
                /*TODO**/
            }
        }else if(id.equals("button_go_citas") || id.equals("button_citas")){
            panel_home.setVisible(false);
            panel_pacientes.setVisible(false);
            panel_medicos.setVisible(false);
            panel_cabecera.setVisible(true);
            panel_citas.setVisible(true);
            //relleno tabla
            rellenaTablaCitas(bbdd.getDoctors().get(0));//prueba
        }else if(id.equals("button_go_medicos") || id.equals("button_medicos")){
            panel_home.setVisible(false);
            panel_citas.setVisible(false);
            panel_pacientes.setVisible(false);
            panel_cabecera.setVisible(true);
            panel_medicos.setVisible(true);
        }else if(id.equals("button_eliminar_doctor")){
            int i = lista_doctores.getSelectionModel().getSelectedIndex();
            if(i > 0){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Eliminar doctor");
                alert.setHeaderText("Atención!");
                alert.setContentText("¿Seguro que quieres eliminar al doctor?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    lista_doctores_a.remove(lista_doctores.getSelectionModel().getSelectedItem());
                    lista_doctores_o = FXCollections.observableArrayList(lista_doctores_a);//porque tengo que hacer eso y no repinta solo?
                    lista_doctores.setItems(lista_doctores_o);
                    //bbdd.saveDB();
                    System.out.println(lista_pacientes.toString());//no es necesario,es feedback
                } else {
                    System.out.println("CANCEL");
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        panel_pacientes.setVisible(false);
        panel_cabecera.setVisible(false);
        panel_medicos.setVisible(false);
        panel_home.setVisible(true);
        //img resizable
        main_fondo_img.fitHeightProperty().bind(panel_home.heightProperty());
        main_fondo_img.fitWidthProperty().bind(panel_home.widthProperty());
        
        
        //cargo la base de datos de los pacientes
        try{
            //declaro la lista de pacientes
            bbdd = ClinicDBAccess.getSingletonClinicDBAccess();
            bbdd.setClinicName("IPC VV Medical Services Clinic");
            
            lista_pacientes_a = bbdd.getPatients();
            lista_pacientes_o = FXCollections.observableArrayList(lista_pacientes_a);
            lista_pacientes.setCellFactory(c -> new PersonListCell());
            lista_pacientes.setItems(lista_pacientes_o.sorted());
            
            lista_doctores_a = bbdd.getDoctors();
            lista_doctores_o = FXCollections.observableList(lista_doctores_a);
            lista_doctores.setCellFactory(b -> new DoctorListCell());
            lista_doctores.setItems(lista_doctores_o);
            //rellenos los choiceBox
            cb_pacientes.setTooltip(new Tooltip("Selecciona al paciente que quiere la cita!"));
            
            ArrayList<String> pacientes = new ArrayList<String>();
            for(int i = 0; i < lista_pacientes_o.size(); i ++){
                pacientes.add(lista_pacientes_o.get(i).getName() + " "+ lista_pacientes_o.get(i).getSurname());
            }
            cb_pacientes.setItems(FXCollections.observableList(pacientes));//lista_pacientes_o
            cb_doctores.setTooltip(new Tooltip("Selecciona al paciente que quiere la cita!"));
            ArrayList<String> doctores = new ArrayList<String>();
            for(int i = 0; i < bbdd.getDoctors().size(); i ++){
                doctores.add(bbdd.getDoctors().get(i).getName() + " "+ bbdd.getDoctors().get(i).getSurname());
            }
            cb_doctores.setItems(FXCollections.observableArrayList(doctores));
            cb_doctores.valueProperty().addListener((a)->{
                rellenaTablaCitas(bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()));
                System.out.println("Elegido: " + bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getName() + " "+
                        bbdd.getDoctors().get(cb_doctores.getSelectionModel().getSelectedIndex()).getSurname());
            });
        }catch(Exception e){System.err.println("Error al cargar la base de datos...");}
    }    

    @FXML
    private void verMedico(ActionEvent event) {
        verInfoMedico(lista_doctores.getSelectionModel().getSelectedItem());
    }
    
    //creamos celda personalizada Paciente
    class PersonListCell extends ListCell<Patient>{
    ImageView view = new ImageView();
    @Override
    protected void updateItem(Patient item, boolean empty){ 
        super.updateItem(item, empty); // Obligatoria esta llamada
        if (item==null || empty) {
        setText(null);
        setGraphic(null);
        }else {
           try{
                setText(item.getName() + " " + item.getSurname());
                view.imageProperty().setValue(item.getPhoto());
                view.setFitHeight(60);
                view.setFitWidth(60);
                setGraphic(view);
           }catch(Exception o){ System.err.print(o.toString());}
        }
    }
    }
    //Creamos lista personalizada Doctor
    class DoctorListCell extends ListCell<Doctor>{
    ImageView view = new ImageView();
    @Override
    protected void updateItem(Doctor item, boolean empty){ 
        super.updateItem(item, empty); // Obligatoria esta llamada
        if (item==null || empty) {
        setText(null);
        setGraphic(null);
        }else {
           try{
                setText(item.getName() + " " + item.getSurname());
                view.imageProperty().setValue(item.getPhoto());
                view.setFitHeight(60);
                view.setFitWidth(60);
                setGraphic(view);
           }catch(Exception o){ System.err.print(o.toString());}
        }
    }
    }
    //volver al menu principal
    @FXML
    private void volver(MouseEvent event) {
        panel_pacientes.setVisible(false);
        panel_medicos.setVisible(false);
        panel_cabecera.setVisible(false);
        panel_home.setVisible(true);
    }
    //cargar la informacion del paciente 
    //hay que cambiarlo
    @FXML
    private void verPaciente(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("FXMLMostrarInfoP.fxml"));
            Parent root = (Parent)loader.load();
            FXMLMostrarInfoPController controlador = loader.<FXMLMostrarInfoPController>getController();
            controlador.rellenaPanel(lista_pacientes.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setTitle("Informacion de " + lista_pacientes.getSelectionModel().getSelectedItem().getName());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch(Exception e){System.err.println("Algo va mal");}
    }

    
    //Recargar la lista
    private void reloadList(){
        lista_pacientes_a = null;
        lista_pacientes_a = bbdd.getPatients();
        lista_pacientes_o = FXCollections.observableArrayList(bbdd.getPatients());
        lista_pacientes.setItems(lista_pacientes_o);
    }

    /**Algoritmo del buscador**/
    private void filtrar(String nombre){
        System.out.print("Lista> ");
        ArrayList<Patient> personas = bbdd.getPatients();
        List<Patient> solucion = new ArrayList<Patient>();
        String[] noms = nombre.trim().split(" ");
        if(noms.length > 0){//>=
            for(int i = 0; i < personas.size(); i ++){
                for(int j = 0; j < noms.length; j ++){
                    if(filtrarPaciente(noms[j].trim().toLowerCase(), personas.get(i).getName().trim().toLowerCase(), personas.get(i).getSurname().trim().toLowerCase())){
                        solucion.add(personas.get(i));
                        lista_pacientes_o = FXCollections.observableArrayList(solucion);
                        lista_pacientes.setItems(lista_pacientes_o);
                    }
                }
            }
        }else {
            System.err.println("Error 2");
        }  
    }
    private  boolean filtrarPaciente(String field, String nombre, String apellido){
        //encuentro apellidos
        String[] terminos = field.trim().split(" ");
        String[] apellidos = apellido.trim().split(" ");
        try {
            for(int i = 0; i < terminos.length; i ++){//field
                for(int j = 0; j < apellidos.length; j ++){
                    if(terminos[i].equals(apellidos[j]) || terminos[i].equals(nombre)) return true;
                }
            }
        }catch(Exception e){System.err.println("FUCKKK!!");}
        
        return false;
    }
    
    /**rellena la tabla de las citas de un medico en especial*/
    private void rellenaTablaCitas(Doctor doctor){
        //inicio la tabla
        //tabla_lunes.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        //tabla_martes.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
       // tabla_miercoles.setCellValueFactory(new PropertyValueFactory<Patient, String>("identifier"));
       // tabla_citas.setItems(FXCollections.observableArrayList(bbdd.getPatients()));
        
        //declaro array para poner huecos
        ArrayList<Days> diasVisita = doctor.getVisitDays();//obtengo los dias de visita
        ArrayList<Appointment> citas = bbdd.getDoctorAppointments(doctor.getIdentifier());
        
        
        
        
        tabla_jueves.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        tabla_citas.setItems(FXCollections.observableArrayList(lista_pacientes_o));//citas
    }
    
    /**Ver informacion del medico**/
    private void verInfoMedico(Doctor doctor){
        int i = lista_doctores.getSelectionModel().getSelectedIndex();
        if(i >= 0){
            profile_m.imageProperty().setValue(doctor.getPhoto());
            label_nombre_medico.setText(doctor.getName());
            label_apellido_medico.setText(doctor.getSurname());
            label_id_medico.setText(doctor.getIdentifier());
            label_tel_medico.setText(doctor.getTelephon());
            label_horainicio_medico.setText(doctor.getVisitStartTime() + "");
            label_horafin_medico.setText(doctor.getVisitEndTime() +"");
            label_sala_medico.setText(doctor.getExaminationRoom().getIdentNumber() + "");
        }
    }
}
