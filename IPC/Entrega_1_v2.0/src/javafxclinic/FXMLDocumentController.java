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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
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
    private GridPane panel_cabecera;
    @FXML
    private Button button_add_n_paciente;
    @FXML
    private Button button_panel_pacientes_buscar;
    private ImageView img_profile;
    private AnchorPane profile;
    @FXML
    protected ListView<Patient> lista_pacientes;
    //protected ArrayList<Patient> lista_pacientes_a;
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
    private ChoiceBox<String> cb_pacientes;
    private ChoiceBox<String> cb_doctores;
    private TableView<Patient> tabla_citas;
    private TableColumn<Patient, String> tabla_jueves;
    @FXML
    private Button button_panel_doctores_buscar1;
    @FXML
    private ListView<Doctor> lista_doctores;
    //private ArrayList<Doctor> lista_doctores_a;
    private ObservableList<Doctor> lista_doctores_o;
    @FXML
    private Button button_eliminar_doctor;
    @FXML
    private GridPane panel_medicos;
    @FXML
    private ImageView profile_m;
    @FXML
    private Button button_ver_datos_medico;
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
    private TextField field_buscador_paciente_citas;
    @FXML
    private ListView<Patient> lista_pacientes_citas;
    private ObservableList<Patient> lista_pacientes_citas_o;
    @FXML
    private ListView<Appointment> lista_citas_citas;
    private ObservableList<Appointment> lista_citas_citas_o;
    private ArrayList<Appointment> lista_citas_citas_a;
    @FXML
    private Button button_add_cita_nueva;
    @FXML
    private Label label_nom_paciente;
    @FXML
    private Label label_apellidos_paciente;
    @FXML
    private Label label_tel_paciente;
    @FXML
    private Label label_identificacion_paciente;
    @FXML
    private ImageView img_profile_paciente;
    @FXML
    private GridPane panel_ver_doctor;
    @FXML
    private Label label_lunes;
    @FXML
    private Label label_martes;
    @FXML
    private Label label_miercoles;
    @FXML
    private Label label_jueves;
    @FXML
    private Label label_viernes;
    @FXML
    private Label label_sabado;
    @FXML
    private Label label_domingo;
    @FXML
    private Button button_go_medicos;
    @FXML
    private Button button_go_citas;
    @FXML
    private Button button_go_pacientes;
    @FXML
    private ImageView button_home;
    @FXML
    private GridPane profile_medico;
    @FXML
    private GridPane panel_img_profile_paciente;
    @FXML
    private GridPane panel_profile_m;
    @FXML
    private GridPane grid_pane_landing_page;
    @FXML
    private TextField field_buscador_medicos;
    @FXML
    private Text titulo_clinica;
    @FXML
    private GridPane info_paciente_p;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        panel_ver_doctor.setVisible(false);
        info_paciente_p.setVisible(false);
        panel_pacientes.setVisible(false);
        panel_cabecera.setVisible(false);
        panel_medicos.setVisible(false);
        panel_home.setVisible(true);
        //img resizable
        main_fondo_img.fitHeightProperty().bind(grid_pane_landing_page.heightProperty());
        main_fondo_img.fitWidthProperty().bind(grid_pane_landing_page.widthProperty());
        //img del perfil del paciente resizable
        img_profile_paciente.fitHeightProperty().bind(panel_img_profile_paciente.heightProperty());
        img_profile_paciente.fitWidthProperty().bind(panel_img_profile_paciente.widthProperty());
        //img del perfil del medico resizable
        profile_m.fitHeightProperty().bind(panel_profile_m.heightProperty());
        profile_m.fitWidthProperty().bind(panel_profile_m.widthProperty());
        //profile_m.fitHeightProperty().bind(profile_medico.heightProperty());
        
        
        //cargo la base de datos de los pacientes
        try{
            //declaro la lista de pacientes
            bbdd = ClinicDBAccess.getSingletonClinicDBAccess();
            bbdd.setClinicName("IPC VV Medical Services Clinic");
            
            ArrayList<Patient> lista_pacientes_a;
            lista_pacientes_a = bbdd.getPatients();
            lista_pacientes_o = FXCollections.observableList(lista_pacientes_a);
            lista_pacientes.setCellFactory(c -> new PersonListCell());
            lista_pacientes.setItems(lista_pacientes_o.sorted());
            
            ArrayList<Doctor> lista_doctores_a;
            lista_doctores_a = bbdd.getDoctors();
            lista_doctores_o = FXCollections.observableList(lista_doctores_a);
            lista_doctores.setCellFactory(b -> new DoctorListCell());
            lista_doctores.setItems(lista_doctores_o);
            
            //declaramos el titulo de la clinica
            titulo_clinica.setText(bbdd.getClinicName());
            
            //lista_citas_citas_a = bbdd.getPatientAppointments(lista_pacientes_citas.getSelectionModel().getSelectedItem().getIdentifier());
            
            lista_pacientes_citas.setCellFactory(c -> new PersonCitaListCell());
            lista_citas_citas.setCellFactory(p -> new PersonVerCitaListCell());
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
        }catch(Exception e){System.err.println("Error al cargar la base de datos...");}
            Pattern pattern = Pattern.compile("[0-9](\\.)[0-9]");
            TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
                return pattern.matcher(change.getControlNewText()).matches() ? change : null;
            });
    }    
    
    @FXML
    private void handleButtonAction(ActionEvent event){
       String id = ((Node)event.getSource()).getId();
       if(id.equals("button_pacientes")|| id.equals("button_go_pacientes")){
           activarBoton(button_go_pacientes);
           panel_home.setVisible(false);
           panel_citas.setVisible(false);
           panel_medicos.setVisible(false);
           panel_pacientes.setVisible(true);
           panel_cabecera.setVisible(true);
       }else if(id.equals("button_add_n_paciente")){ //boton de añadir los nuevos pacientes
           info_paciente_p.setVisible(false);
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAddPaciente.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLAddPacienteController controlador = fxmlLoader.<FXMLAddPacienteController>getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Añadir paciente");
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                if(!stage.isShowing()){
                    System.out.println("UPS");
                    //lista_pacientes_a.add(controlador.getPatient());//bbdd.getPatients().get(lista_pacientes_a.size()));        
                    //lista_pacientes_o = FXCollections.observableArrayList(lista_pacientes_a);//porque tengo que hacer eso y no repinta solo?
                    //lista_pacientes.setItems(lista_pacientes_o);
                    if(!controlador.getCancelar()){
                        Patient p = controlador.getPatient();
                        lista_pacientes_o.add(p);
                        try{
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmacion.fxml"));
                            Parent root2 = (Parent) loader.load();
                            Stage stage1 = new Stage();
                            stage1.setScene(new Scene(root2));
                            stage1.initModality(Modality.APPLICATION_MODAL);
                            stage1.setResizable(false);
                            stage1.initStyle(StageStyle.UNDECORATED);
                            stage1.show();
                        }catch(Exception e){System.err.println(e);}
                    }
                }
                reloadList();
            }catch(Exception o){System.err.print(o.toString());}
        }else if(id.equals("button_eliminar_paciente")){ //boton de eliminar al paciente
            info_paciente_p.setVisible(false);
            int i = lista_pacientes.getSelectionModel().getSelectedIndex();
            if(i >= 0){//comprobar si esta seleccionada
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Eliminar paciente");
                alert.setHeaderText("Atención!");
                alert.setContentText("¿Seguro que quieres eliminar al paciente?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK && !bbdd.hasAppointments(lista_pacientes.getSelectionModel().getSelectedItem())){
                    //lista_pacientes_a.remove(lista_pacientes.getSelectionModel().getSelectedItem());//cambiado
                    //lista_pacientes_o = FXCollections.observableArrayList(lista_pacientes_a);//cambiado
                    lista_pacientes_o.remove(lista_pacientes.getSelectionModel().getSelectedItem());
                    //lista_pacientes.setItems(lista_pacientes_o);//cambiado
                    //bbdd.saveDB();
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmacion.fxml"));
                        Parent root2 = (Parent) loader.load();
                        Stage stage1 = new Stage();
                        stage1.setScene(new Scene(root2));
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setResizable(false);
                        stage1.initStyle(StageStyle.UNDECORATED);
                        stage1.show();
                    }catch(Exception e){System.err.println(e);}
                    System.out.println(lista_pacientes.toString());
                } else {
                    System.out.println("CANCEL");
                    Alert alert2 = new Alert(AlertType.ERROR);
                    alert2.setTitle("Operación cancelada");
                    alert2.setHeaderText("Atención!");
                    alert2.setContentText("El paciente tiene citas pendientes...");
                    alert2.showAndWait();
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
                lista_pacientes_o = FXCollections.observableList(bbdd.getPatients());//arrayList
                lista_pacientes.setItems(lista_pacientes_o);
                /*TODO**/
            }
        }else if(id.equals("button_go_citas") || id.equals("button_citas")){
            activarBoton(button_go_citas);
            //lista_pacientes_citas_o = FXCollections.observableList(lista_pacientes_a);
            lista_pacientes_citas_o = FXCollections.observableList(bbdd.getPatients());
            lista_pacientes_citas.setItems(lista_pacientes_citas_o);
            panel_home.setVisible(false);
            panel_pacientes.setVisible(false);
            panel_medicos.setVisible(false);
            panel_cabecera.setVisible(true);
            panel_citas.setVisible(true);
        }else if(id.equals("button_go_medicos") || id.equals("button_medicos")){
            activarBoton(button_go_medicos);
            panel_home.setVisible(false);
            panel_citas.setVisible(false);
            panel_pacientes.setVisible(false);
            panel_cabecera.setVisible(true);
            panel_medicos.setVisible(true);
        }else if(id.equals("button_eliminar_doctor")){
            panel_ver_doctor.setVisible(false);
            int i = lista_doctores.getSelectionModel().getSelectedIndex();
            if(i >= 0){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Eliminar doctor");
                alert.setHeaderText("Atención!");
                alert.setContentText("¿Seguro que quieres eliminar al doctor?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK && !bbdd.hasAppointments(lista_doctores.getSelectionModel().getSelectedItem())){
                    /*lista_doctores_a.remove(lista_doctores.getSelectionModel().getSelectedItem());
                    lista_doctores_o = FXCollections.observableArrayList(lista_doctores_a);//porque tengo que hacer eso y no repinta solo?
                    lista_doctores.setItems(lista_doctores_o);*/
                    lista_doctores_o.remove(lista_doctores.getSelectionModel().getSelectedItem());
                    //bbdd.saveDB();
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmacion.fxml"));
                        Parent root2 = (Parent) loader.load();
                        Stage stage1 = new Stage();
                        stage1.setScene(new Scene(root2));
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setResizable(false);
                        stage1.initStyle(StageStyle.UNDECORATED);
                        stage1.show();
                    }catch(Exception e){System.err.println(e);}
                    System.out.println(lista_pacientes.toString());//no es necesario,es feedback
                } else {
                    System.out.println("CANCEL");
                    Alert alert2 = new Alert(AlertType.ERROR);
                    alert2.setTitle("Operación cancelada");
                    alert2.setHeaderText("Atención!");
                    alert2.setContentText("El doctor tiene citas pendientes...");
                    alert2.showAndWait();
                }
            }
        }else if(id.equals("button_salir")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso!");
            alert.setHeaderText("Guardando modificaciones");
            alert.setContentText("Se van a guardar los cambios en la base de datos.Pulse aceptar para acabar.");
            alert.showAndWait();
            bbdd.saveDB();
            Platform.exit();
        }else if(id.equals("button_add_n_doctor")){
            panel_ver_doctor.setVisible(false);
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAnyadirDoctor.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                FXMLAnyadirDoctorController controlador = fxmlLoader.<FXMLAnyadirDoctorController>getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.setTitle("Añadir doctor");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.showAndWait();
                if(!controlador.getCancelar()) {
                    Doctor d = controlador.getDoctor();
                    //lista_doctores_a.add(d);//cosas cambiadas
                    bbdd.getExaminationRooms().add(d.getExaminationRoom());
                    //lista_doctores_o= FXCollections.observableArrayList(lista_doctores_a);//cosas cambiadas
                    //lista_doctores.setItems(lista_doctores_o);//cosas cambiadas
                    lista_doctores_o.add(d);
                    //bbdd.saveDB();
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmacion.fxml"));
                        Parent root2 = (Parent) loader.load();
                        Stage stage1 = new Stage();
                        stage1.setScene(new Scene(root2));
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setResizable(false);
                        stage1.initStyle(StageStyle.UNDECORATED);
                        stage1.show();
                    }catch(Exception e){System.err.println(e);}
                }
            }catch(IOException o){System.out.println(o);}
        }
    }
    @FXML
    private void verMedico(ActionEvent event) {
        panel_ver_doctor.setVisible(true);
        verInfoMedico(lista_doctores.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void addCitaNueva(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAddCita.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            FXMLAddCitaController controlador = fxmlLoader.<FXMLAddCitaController>getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Añadir cita");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            if(!controlador.getCancelar()){
                lista_citas_citas_a = bbdd.getPatientAppointments(controlador.getPaciente().getIdentifier());
                lista_citas_citas_a.add(controlador.getAppointment());
                lista_citas_citas_o = FXCollections.observableList(lista_citas_citas_a);
                lista_citas_citas.setItems(lista_citas_citas_o);
                bbdd.getAppointments().add(controlador.getAppointment());
                bbdd.getDoctorAppointments(controlador.getMedico().getIdentifier()).add(controlador.getAppointment());
                bbdd.getPatientAppointments(controlador.getPaciente().getIdentifier()).add(controlador.getAppointment());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmacion.fxml"));
                Parent root2 = (Parent) loader.load();
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root2));
                stage1.setTitle("Añadir cita");
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.setResizable(false);
                stage1.initStyle(StageStyle.UNDECORATED);
                stage1.show();
            }
        }catch(Exception o){System.err.print(o.toString()); o.printStackTrace();}
    }

    @FXML
    private void buscarCitas(KeyEvent event) {
        if(!field_buscador_paciente_citas.getText().isEmpty()){
                try{filtrarCitas(field_buscador_paciente_citas.getText());}catch(Exception e){System.out.println("Algo fallo");}
                //filtrarPaciente(field_buscador.getText(), bbdd.getPatients());
        }else{
           // reloadList();
           System.err.println("Error 1");
            lista_pacientes_citas_o = FXCollections.observableList(bbdd.getPatients());//porque tengo que hacer eso y no repinta solo?
            lista_pacientes_citas.setItems(lista_pacientes_citas_o);
            /*TODO**/
        }
    }

    @FXML
    private void filtrarDoctorB(ActionEvent event) {
        if(!field_buscador_medicos.getText().isEmpty()){
                try{filtrarDoctor(field_buscador_medicos.getText());}catch(Exception e){System.out.println("Algo fallo");}
                //filtrarPaciente(field_buscador.getText(), bbdd.getPatients());
        }else{
           // reloadList();
           System.err.println("Error 1");
            lista_doctores_o = FXCollections.observableList(bbdd.getDoctors());//arrayList
            lista_doctores.setItems(lista_doctores_o);
            /*TODO**/
        }
    }

    @FXML
    private void filtrarDoctor(KeyEvent event) {
        if(!field_buscador_medicos.getText().isEmpty()){
                try{filtrarDoctor(field_buscador_medicos.getText());}catch(Exception e){System.out.println("Algo fallo");}
                //filtrarPaciente(field_buscador.getText(), bbdd.getPatients());
        }else{
           // reloadList();
           System.err.println("Error 1");
            lista_doctores_o = FXCollections.observableList(bbdd.getDoctors());//arrayList
            lista_doctores.setItems(lista_doctores_o);
            /*TODO**/
        }
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
    
    //creamos celda personalizada PacienteCita
    class PersonCitaListCell extends ListCell<Patient>{
    /**Cell style de rellnar las cosas extra (trabajos etc) de las asignaturas**/
        HBox hbox = new HBox();//contenedor
        Button citas = new Button("Citas");
        Label nombre = new Label("");
        Label apellidos = new Label("");
        ImageView foto = new ImageView();
        Pane pane = new Pane();
        public PersonCitaListCell() {
            super();   
            hbox.setAlignment(Pos.CENTER);
            //citas.getStyleClass().add("button_mostrar_info");
            citas.setStyle("-fx-background-color: white;-fx-background-radius: 80px;-fx-border-color: #004DFF;-fx-border-radius: 80px;");
            citas.setOnAction(e -> {
                try{
                    if(this.isSelected()){
                        System.out.println("Mostrando Citas de: "+ lista_pacientes_citas.getSelectionModel().getSelectedItem().getName());
                        ArrayList<Appointment> lista_citas_citas_a;
                        lista_citas_citas_a = bbdd.getPatientAppointments(lista_pacientes_citas.getSelectionModel().getSelectedItem().getIdentifier());//alomejor va fatal
                        lista_citas_citas_o = FXCollections.observableList(lista_citas_citas_a);
                        lista_citas_citas.setItems(lista_citas_citas_o);
                        lista_citas_citas.setVisible(true);
                    }else {System.out.println("Seleccionalo");}
                }catch(Exception a){ System.err.println("UPS otro");}
            });
            hbox.getChildren().addAll(foto,nombre, apellidos, pane, citas);
            hbox.setHgrow(pane, Priority.ALWAYS);
        }
        public void updateItem(Patient name, boolean empty) {
            super.updateItem(name, empty);
            setText(null);
            setGraphic(null);        
            if(name != null && !empty) {
                foto.imageProperty().setValue(name.getPhoto());
                foto.setFitHeight(60);
                foto.setFitWidth(60);
                nombre.setText("   "+name.getName());
                apellidos.setText("   "+name.getSurname());
                setGraphic(hbox);
            }
        }       
    }
    
    //creamos celda personalizada PacienteCita
    class PersonVerCitaListCell extends ListCell<Appointment>{
    /**Cell style de rellnar las cosas extra (trabajos etc) de las asignaturas**/
        HBox hbox = new HBox();//contenedor
        VBox vbox = new VBox();//contenedor
        Button verCita = new Button("Ver cita");
        Button eliminar = new Button("Eliminar");
        Label cita = new Label("");
        Label fecha = new Label("");
        Pane pane = new Pane();
        Doctor doctor;
        public PersonVerCitaListCell() {
            super();   
            hbox.setAlignment(Pos.CENTER);
            verCita.setStyle("-fx-background-color: white;-fx-background-radius: 80px;-fx-border-color: #004DFF;-fx-border-radius: 80px;");
            eliminar.setStyle("-fx-background-color: white;-fx-background-radius: 80px;-fx-border-color: red;-fx-border-radius: 80px;");
            cita.setMaxWidth(Region.USE_COMPUTED_SIZE);
            vbox.setStyle("-fx-border-width: 2px; -fx-border-color: blue;-fx-border-radius:50px;");
            vbox.setPadding(new Insets(10, 10, 10, 10));  
            vbox.setSpacing(5);
            
            verCita.setOnAction(e -> {
                try{
                    if(this.isSelected()){
                        System.out.println("Mostrando Citas de: "+ lista_pacientes_citas.getSelectionModel().getSelectedItem().getName());
                        lista_citas_citas.getSelectionModel().getSelectedItem().getAppointmentDateTime().toString();
                        
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMostrarInfoCita.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        FXMLMostrarInfoCitaController controlador = fxmlLoader.<FXMLMostrarInfoCitaController>getController();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.setTitle("Informacion de la cita");
                        stage.setResizable(false);
                        
                        stage.initModality(Modality.APPLICATION_MODAL);
                        controlador.setWindow((bbdd.getPatientAppointments(lista_pacientes_citas.getSelectionModel().getSelectedItem().getIdentifier()).get(lista_citas_citas.getSelectionModel().getSelectedIndex())));
                        System.err.println(lista_pacientes_citas.getSelectionModel().getSelectedItem().getName());
                        stage.showAndWait();
                    }else {System.out.println("Seleccionalo");}
                }catch(Exception a){ System.err.println("UPS parece ser que hay un error aqui, tome, mirelo: " + a.toString());}
            });
            
            eliminar.setOnAction(ee->{
                try{
                    if(this.isSelected()){
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Eliminar cita");
                        alert.setHeaderText("Esta seguro que de quiere eliminarla?");
                        alert.setContentText("Al aceptar, se perdera para la cita");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK){
                            if(this.isSelected() && !lista_citas_citas.getSelectionModel().getSelectedItem().getAppointmentDateTime().isBefore(LocalDateTime.now())){
                                System.out.println("OK");
                                lista_citas_citas_o.remove(lista_citas_citas.getSelectionModel().getSelectedIndex());//elimino de la lista
                                bbdd.getAppointments().remove(lista_citas_citas.getSelectionModel().getSelectedIndex());
                                bbdd.getDoctorAppointments(lista_citas_citas.getSelectionModel().getSelectedItem().getDoctor().getIdentifier());
                                bbdd.getPatientAppointments(lista_citas_citas.getSelectionModel().getSelectedItem().getPatient().getIdentifier());
                                
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConfirmacion.fxml"));
                                Parent root2 = (Parent) loader.load();
                                Stage stage1 = new Stage();
                                stage1.setScene(new Scene(root2));
                                stage1.setTitle("Añadir cita");
                                stage1.initModality(Modality.APPLICATION_MODAL);
                                stage1.setResizable(false);
                                stage1.initStyle(StageStyle.UNDECORATED);
                                stage1.show();
                            }else {
                                Alert alerta = new Alert(AlertType.INFORMATION);
                                alerta.setTitle("Alerta");
                                alerta.setHeaderText("No puede eliminar una cita pasada");
                                alerta.setContentText("O tal vez no seleccionó una cita primero");
                                alerta.showAndWait();
                            }
                        } else {
                            System.out.println("CANCEL");
                        }
                    }else {System.out.println("Seleccionalo");}
                }catch(Exception a){ System.err.println("UPS parece ser que hay un error aqui, tome, mirelo: " + a.toString());}
            });
            verCita.getStyleClass().add("button_mostrar_info");
            vbox.getChildren().addAll(cita,fecha);
            hbox.getChildren().addAll(vbox,pane,verCita, eliminar);
            hbox.setHgrow(pane, Priority.ALWAYS);
        }
        public void updateItem(Appointment name, boolean empty) {
            super.updateItem(name, empty);
            setText(null);
            setGraphic(null);        
            if(name != null && !empty) {
                cita.setText("Dr/Dra: " +name.getDoctor().getName()+ " " +
                        name.getDoctor().getSurname()+".");
                fecha.setText(" Fecha: " +name.getAppointmentDateTime().toString());
                setGraphic(hbox);
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
    @FXML
    private void verPaciente(ActionEvent event) throws IOException {
        info_paciente_p.setVisible(true);
        if(lista_pacientes.getSelectionModel().getSelectedIndex() >= 0){
            label_nom_paciente.setText(lista_pacientes.getSelectionModel().getSelectedItem().getName());
            label_tel_paciente.setText(lista_pacientes.getSelectionModel().getSelectedItem().getTelephon());
            label_apellidos_paciente.setText(lista_pacientes.getSelectionModel().getSelectedItem().getSurname());
            label_identificacion_paciente.setText(lista_pacientes.getSelectionModel().getSelectedItem().getIdentifier());
            img_profile_paciente.setImage(lista_pacientes.getSelectionModel().getSelectedItem().getPhoto());
        }
    }

    
    //Recargar la lista
    private void reloadList(){
        /*lista_pacientes_a = null;
        lista_pacientes_a = bbdd.getPatients();
        lista_pacientes_o = FXCollections.observableArrayList(bbdd.getPatients());
        lista_pacientes.setItems(lista_pacientes_o);*/
    }

    /**Algoritmo del buscador filtrado por nombre y apellidos**/
    /*private void filtrar(String nombre){
        try{
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
        }catch(Exception e){
            System.out.print("Algo fallo, pero nada saldra mal :)");
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
    }*/
    /**@param String paciente
     *calcula la lista de pacientes en el buscador de pacientes
     */
    private void filtrar(String nombre){
        try{
            System.out.print("Lista> ");
            ArrayList<Patient> personas = bbdd.getPatients();
            List<Patient> solucion = new ArrayList<Patient>();
            String[] noms = nombre.trim().split(" ");
            if(noms.length > 0){//>=
                for(int i = 0; i < personas.size(); i ++){
                    for(int j = 0; j < noms.length; j ++){
                        if(filtrarPersona(noms[j].trim().toLowerCase(), personas.get(i).getIdentifier().trim().toLowerCase())){
                            solucion.add(personas.get(i));
                            lista_pacientes_o = FXCollections.observableList(solucion);//arrayList
                            lista_pacientes.setItems(lista_pacientes_o);
                        }
                    }
                }
            }else {
                System.err.println("Error 2");
            } 
        }catch(Exception e){
            System.out.print("Algo fallo, pero nada saldra mal :)");
        } 
    }
    /**@param String paciente
     *calcula la lista de pacientes en el buscador de pacientes
     */
    private void filtrarDoctor(String nombre){
        try{
            System.out.print("Lista> ");
            ArrayList<Doctor> personas = bbdd.getDoctors();
            List<Doctor> solucion = new ArrayList<Doctor>();
            String[] noms = nombre.trim().split(" ");
            if(noms.length > 0){//>=
                for(int i = 0; i < personas.size(); i ++){
                    for(int j = 0; j < noms.length; j ++){
                        if(filtrarPersona(noms[j].trim().toLowerCase(), personas.get(i).getIdentifier().trim().toLowerCase())){
                            solucion.add(personas.get(i));
                            lista_doctores_o = FXCollections.observableList(solucion);//arrayList
                            lista_doctores.setItems(lista_doctores_o);
                        }
                    }
                }
            }else {
                System.err.println("Error 2");
            } 
        }catch(Exception e){
            System.out.print("Algo fallo, pero nada saldra mal :)");
        } 
    }
    /**@param String paciente
     *calcula la lista de medico en el buscador de medico
     */
    private void filtrarCitas(String nombre){
        try{
            System.out.print("Lista> ");
            ArrayList<Patient> personas = bbdd.getPatients();
            List<Patient> solucion = new ArrayList<Patient>();
            String[] noms = nombre.trim().split(" ");
            if(noms.length > 0){//>=
                for(int i = 0; i < personas.size(); i ++){
                    for(int j = 0; j < noms.length; j ++){
                        if(filtrarPersona(noms[j].trim().toLowerCase(), personas.get(i).getIdentifier().trim().toLowerCase())){
                            System.err.println("Encontrado");
                            solucion.add(personas.get(i));
                            lista_pacientes_citas_o = FXCollections.observableList(solucion);//arrayList
                            lista_pacientes_citas.setItems(lista_pacientes_citas_o);
                        }
                    }
                }
            }else {
                System.err.println("Error 2");
            } 
        }catch(Exception e){
            System.out.print("Algo fallo, pero nada saldra mal :)");
        } 
    }
    /**buscador por Identificacion
     @param String field del buscador
     @param String id del objetivo a buscar
     @return Boolean solucion de si existe coincidencia o no**/
    private  boolean filtrarPersona(String field, String id){
        //encuentro apellidos
        String[] terminos = field.trim().split(" ");
        try {
            for(int i = 0; i < terminos.length; i ++){//field
                    if(terminos[i].equals(id)) return true;
            }
        }catch(Exception e){System.err.println("Algo ha ido mal!!");}
        
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
        tabla_citas.setItems(FXCollections.observableList(lista_pacientes_o));//citas
    }
    /**metodo que ambia de color al boton seleccionado**/
    private void activarBoton(Button b){
        //calcelo los colores de todos los botones
        button_go_pacientes.setStyle("-fx-border-color:#7C9DEA; -fx-border-width:0px;");//-fx-background-color:#7C9DEA");
        button_go_medicos.setStyle("-fx-border-color: #7C9DEA; -fx-border-width:0px;");//-fx-background-color:#7C9DEA");
        button_go_citas.setStyle("-fx-border-color:#7C9DEA; -fx-border-width:0px;");//-fx-background-color: #7C9DEA");
        //le pongo el color de activo al boton seleccionado
        b.setStyle("-fx-border-color:#004DFF; -fx-border-width:4px;");//-fx-background-color:white;");
    }
    /**Ver informacion del medico**/
    private void verInfoMedico(Doctor doctor){
        panel_ver_doctor.setVisible(true);
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
            ArrayList<Days> diasVisita = doctor.getVisitDays();
            if(diasVisita.contains(Days.Monday)){label_lunes.setStyle("-fx-border-color: #32FF00; -fx-border-width: 2px;");}
            else {label_lunes.setStyle("-fx-border-color: #960000");}
            if(diasVisita.contains(Days.Tuesday)){label_martes.setStyle("-fx-border-color: #32FF00;-fx-border-width: 2px;");}
            else {label_martes.setStyle("-fx-border-color: #960000");}
            if(diasVisita.contains(Days.Wednesday)){label_miercoles.setStyle("-fx-border-color: #32FF00;-fx-border-width: 2px;");}
            else {label_miercoles.setStyle("-fx-border-color: #960000");}
            if(diasVisita.contains(Days.Thursday)){label_jueves.setStyle("-fx-border-color: #32FF00;-fx-border-width: 2px;");}
            else {label_jueves.setStyle("-fx-border-color: #960000");}
            if(diasVisita.contains(Days.Friday)){label_viernes.setStyle("-fx-border-color: #32FF00;-fx-border-width: 2px;");}
            else {label_viernes.setStyle("-fx-border-color: #960000");}
            if(diasVisita.contains(Days.Saturday)){label_sabado.setStyle("-fx-border-color: #32FF00;-fx-border-width: 2px;");}
            else{label_sabado.setStyle("-fx-border-color: #960000");}
            if(diasVisita.contains(Days.Sunday)){label_domingo.setStyle("-fx-border-color: #32FF00;-fx-border-width: 2px;");}
            else{label_domingo.setStyle("-fx-border-color: #960000");}
        }
    }
}
