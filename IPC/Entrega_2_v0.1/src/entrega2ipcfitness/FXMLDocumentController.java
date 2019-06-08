/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega2ipcfitness;

import accesoBD.AccesoBD;
import static accesoBD.AccesoBD.getInstance;
import java.awt.Panel;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;
import modelo.SesionTipo;

/**
 *
 * @author vlama
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private GridPane main_panel;
    @FXML
    private GridPane clock_panel;
    @FXML
    private Text label_clock;
    @FXML
    private ImageView reboot_button;
    @FXML
    private ImageView pause_button;
    @FXML
    private ImageView play_button;
    @FXML
    private Label label_round;
    @FXML
    private Label label_step;
    @FXML
    private Label type_label;
    @FXML
    private GridPane menu_panel;
    @FXML
    private Button group_button;
    @FXML
    private Button sesion_button;
    @FXML
    private Button graphic_button;
    @FXML
    private Button exit_button;
    
    private String timer = "00";
    private long nowTime;
    
    private boolean menuActive = false;
    @FXML
    private ComboBox<String> combo_choice_group;
    ArrayList<Grupo> ch_group_a;
    private ObservableList<Grupo> ch_group_o;
    @FXML
    private ComboBox<String> combo_choice_sesion;
    ArrayList<SesionTipo> ch_sesion_a;
    private ObservableList<SesionTipo> ch_sesion_o;
    
    
    private AccesoBD bbdd;
    @FXML
    private GridPane group_panel;
    @FXML
    private ListView<Grupo> group_list_view;
    @FXML
    private Button add_group;
    @FXML
    private GridPane add_mod_group_panel;
    @FXML
    private TextField field_group_code;
    @FXML
    private TextArea text_field_group_description;
    private TextField field_group_warm_up;
    private TextField field_group_number_exercice;
    private TextField field_group_time_exercice;
    private TextField field_group_rest_exercice;
    private TextField field_group_number_circuits;
    private TextField field_group_rest_circuit;
    @FXML
    private Button save_group;
    @FXML
    private GridPane sesion_panel;
    @FXML
    private ListView<String> sesion_list_view;
    @FXML
    private Text error_cod;
    private Text error_calentamiento;
    private Text error_ejercicios;
    private Text error_t_ejercicios;
    private Text error_des_ejercicio;
    private Text error_num_circuito;
    private Text error_des_circuito;
    @FXML
    private GridPane add_mod_group_panel1;
    @FXML
    private TextField field_sesion_warm_up;
    @FXML
    private TextField field_sesion_number_exercice;
    @FXML
    private TextField field_sesion_time_exercice;
    @FXML
    private TextField field_sesion_rest_exercice;
    @FXML
    private TextField field_sesion_number_circuits;
    @FXML
    private TextField field_sesion_rest_circuit;
    @FXML
    private Button save_sesion;
    @FXML
    private Text error_calentamiento1;
    @FXML
    private Text error_ejercicios1;
    @FXML
    private Text error_t_ejercicios1;
    @FXML
    private Text error_des_ejercicio1;
    @FXML
    private Text error_num_circuito1;
    @FXML
    private Text error_des_circuito1;
    @FXML
    private Text error_codigo1;
    private TextField field_group_code1;
    @FXML
    private Text error_cod1;
    @FXML
    private TextField field_sesion_code;
    private ClockTimer task;
    
    
    //Control Play/Pause
    SesionTipo actual;
    private int tCalentamientoActual = 1;//tiempo transcurrido
    private int nEjercicios = 1;//bucle de ejercicios
    private int tEjerciciosActual = 1;//tiempo transcurrido de ejercicios
    private int dEjerciciosActual = 1;//tiempo de ejercicios transcurridp
    private int nCircuitos = 1;//bucle circuitos
    private int dCircuito = 1;//tiempo descanso entre circuitos
    private boolean CronometroEjecutandose = false;
    private long horaInicio = 0;
    private Duration horaFinal;
    private LocalDateTime tiempoLocalIni;
    
    @FXML
    private Button home_menu;
    @FXML
    private Label label_exercice;
    //songs
    private File finish;
    private File start;
    @FXML
    private GridPane graphics_panel;
    @FXML
    private Spinner<Integer> select_group_spinner;
    
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    @FXML
    private LineChart<String, Number> grafica;
    @FXML
    private ChoiceBox<String> select_group;
    @FXML
    private ImageView group_img;
    @FXML
    private Text error_main_text;
    private boolean controlHabilitado = false;
    private boolean controlHabilitado2 = false;
    private boolean haFinalizado = true;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        

        finish = new File("src"+ File.separator+"mp3" +File.separator + "finish.wav");
        start = new File("src"+ File.separator+"mp3" +File.separator + "start.wav");
        //creating gym :P
        bbdd = getInstance();
        menuActive = false;
        menu_panel.setVisible(true);
        group_panel.setVisible(false);
        sesion_panel.setVisible(false);
        graphics_panel.setVisible(false);
        main_panel.setVisible(true);
        
        
        ch_group_a = bbdd.getGym().getGrupos();
        ch_group_o = FXCollections.observableList(ch_group_a);
        group_list_view.setItems(ch_group_o);
        
        
        //combo_choice_group.setItems(ch_group_o);//.setItems(ch_group_o);
        //combo_choice_group.setCellFactory(v -> new GroupCellCombo());
        
        //creating sessions :)
        ch_sesion_a = bbdd.getGym().getTiposSesion();
        ch_sesion_o = FXCollections.observableList(ch_sesion_a);
        //combo_choice_sesion.setItems(ch_sesion_o);
        //sesion_list_view.setItems(ch_sesion_o);
        //ch_sesion_a.toArray().toString()
        //BORRRRAARRARARARA
        //setting graphics selection checkbox
        ArrayList<Grupo> aGrupo = bbdd.getGym().getGrupos();
        ArrayList<String> al = new ArrayList();
        for(int i = 0; i < aGrupo.size(); i++ ){
            al.add(aGrupo.get(i).getCodigo());
        }
        select_group.setItems(FXCollections.observableList(al));
        combo_choice_group.setItems(FXCollections.observableList(al));
        
        ArrayList<SesionTipo> aSesionTipo = bbdd.getGym().getTiposSesion();
        ArrayList<String> aS = new ArrayList();
        for(int i = 0; i < aSesionTipo.size(); i++ ){
            aS.add(aSesionTipo.get(i).getCodigo());
        }
        sesion_list_view.setItems(FXCollections.observableList(aS));
        combo_choice_sesion.setItems(FXCollections.observableList(aS));
        //detecta la sesion elegida
        combo_choice_sesion.valueProperty().addListener((o, old, nev) -> {
            //actual = combo_choice_sesion.getSelectionModel().getSelectedItem();
            actual = aSesionTipo.get(combo_choice_sesion.getSelectionModel().getSelectedIndex()); 
            //combo_choice_sesion.setPromptText(combo_choice_sesion.getSelectionModel().getSelectedItem().getCodigo());
            type_label.setText(actual.getCodigo());
            //combo_choice_sesion.setValue(combo_choice_sesion.getSelectionModel().getSelectedItem());
            combo_choice_sesion.valueProperty().asString();
            controlHabilitado = true;
        });
        //detects group changes
        combo_choice_group.valueProperty().addListener((o, old, nev) -> {
            //actualS = aGrupo.get(combo_choice_group.getSelectionModel().getSelectedIndex()); 
            controlHabilitado2 = true;
        });
        //detects group selection for the graphic
        select_group.valueProperty().addListener((o,old,nev)-> {
            try{
                grafica.getData().clear();
                Grupo g = bbdd.getGym().getGrupos().get(select_group.getSelectionModel().getSelectedIndex());
                ArrayList<Sesion> sesiones = g.getSesiones();
                //creating series
                XYChart.Series series1 = new XYChart.Series();
                series1.setName("tEjercicio");
                XYChart.Series series2 = new XYChart.Series();
                series2.setName("tDescanso");
                XYChart.Series series3 = new XYChart.Series();
                series3.setName("tTotal");
                select_group_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,sesiones.size(),0));

                for(int i =0; i < select_group_spinner.getValue(); i ++){
                    //series.setName("My portfolio");
                    series1.getData().add(new XYChart.Data("Sesión"+i+": "+sesiones.get(i).getTipo().getCodigo()+"", sesiones.get(i).getTipo().getNum_circuitos() * ((sesiones.get(i).getTipo().getNum_ejercicios() *sesiones.get(i).getTipo().getT_ejercicio()) 
                                                                                                + sesiones.get(i).getTipo().getT_calentamiento())));
                    series2.getData().add(new XYChart.Data("Sesión"+i+": "+sesiones.get(i).getTipo().getCodigo()+"", sesiones.get(i).getTipo().getD_circuito() * sesiones.get(i).getTipo().getNum_ejercicios()));
                    series3.getData().add(new XYChart.Data("Sesión"+i+": "+sesiones.get(i).getTipo().getCodigo()+"", sesiones.get(i).getDuracion().getSeconds()));
                }
                grafica.getData().add(series3);
                grafica.getData().add(series1);
                grafica.getData().add(series2);
            }catch(Exception e){
                System.err.println("Fue introducido un mal dato...");
            }
        });
        
        select_group_spinner.valueProperty().addListener((o,old,nev)->{
            setGraf();
        });
        //adding cell factories
        group_list_view.setCellFactory(e -> new GroupCell());
        
        //save_group.disableProperty().bind(Bindings.equal(-1, group_list_view.getSelectionModel().selectedIndexProperty()));
        save_group.setDisable(true);
    }    

    //method of graphic
    private void setGraf(){
        try{
            grafica.getData().clear();
            Grupo g = bbdd.getGym().getGrupos().get(select_group.getSelectionModel().getSelectedIndex());
            ArrayList<Sesion> sesiones = g.getSesiones();
            //creating series
            XYChart.Series series1 = new XYChart.Series();
            series1.setName("tEjercicio");
            XYChart.Series series2 = new XYChart.Series();
            series2.setName("tDescanso");
            XYChart.Series series3 = new XYChart.Series();
            series3.setName("tTotal");
            //select_group_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,sesiones.size(),0));

            for(int i =0; i < select_group_spinner.getValue(); i ++){
                //series.setName("My portfolio");
                series1.getData().add(new XYChart.Data("Sesión"+i+": "+sesiones.get(i).getTipo().getCodigo()+"", sesiones.get(i).getTipo().getNum_circuitos() * ((sesiones.get(i).getTipo().getNum_ejercicios() *sesiones.get(i).getTipo().getT_ejercicio()) 
                                                                                            + sesiones.get(i).getTipo().getT_calentamiento())));
                series2.getData().add(new XYChart.Data("Sesión"+i+": "+sesiones.get(i).getTipo().getCodigo()+"", sesiones.get(i).getTipo().getD_circuito() * sesiones.get(i).getTipo().getNum_ejercicios()));
                series3.getData().add(new XYChart.Data("Sesión"+i+": "+sesiones.get(i).getTipo().getCodigo()+"", sesiones.get(i).getDuracion().getSeconds()));
            }
            grafica.getData().add(series3);
            grafica.getData().add(series1);
            grafica.getData().add(series2);
        }catch(Exception e){
            System.err.println("Alguien introdujo un mal numero :(...");
        }
    }
    @FXML
    private synchronized void controlTime(MouseEvent event) {
        String button = ((Node)event.getSource()).getId();
        if(controlHabilitado && controlHabilitado2){
            if(button.equals("play_button")){
                //datos
                if(!CronometroEjecutandose){
                    if(haFinalizado){
                        horaInicio = System.currentTimeMillis();
                        haFinalizado = false;
                    }
                    //changing opacity
                    play_button.setOpacity(0.6);
                    pause_button.setOpacity(1);
                    reboot_button.setOpacity(1);
                    
                    tiempoLocalIni = LocalDateTime.now();
                    System.out.println("Play" + ch_sesion_a.toString());
                    CronometroEjecutandose = true;
                    combo_choice_sesion.setDisable(true);
                    combo_choice_group.setDisable(true);
                    task = new ClockTimer();
                    Thread th = new Thread(task);
                    th.setDaemon(true);
                    th.start();
                }
            }else if(button.equals("pause_button")){
                if(CronometroEjecutandose){
                    CronometroEjecutandose = false;
                    //changing opacity
                    play_button.setOpacity(1);
                    pause_button.setOpacity(0.6);
                    reboot_button.setOpacity(1);
                    System.out.println("Pause");
                    task.cancel();
                }
            }else if(button.equals("reboot_button")){
                if(CronometroEjecutandose){
                    CronometroEjecutandose = false;
                    task.cancel();
                    //changing opacity
                    play_button.setOpacity(1);
                    pause_button.setOpacity(1);
                    reboot_button.setOpacity(0.6);
                    //resetting vars
                    tCalentamientoActual = 1;
                    nEjercicios = 1;
                    tEjerciciosActual = 1;
                    dEjerciciosActual = 1;
                    nCircuitos = 1;
                    dCircuito = 1;
                    System.out.println("Rebooting");

                }
            }
        }else {
            new Thread(()->{
                System.out.println("Ojito");
                error_main_text.setVisible(true);
                try{Thread.sleep(5000);}catch(Exception e ){System.out.println("Durmiendo??");}
                error_main_text.setVisible(false);
            }).start();
        }
    }
    
    //reinicia los parametros de la pantalla
    private void mostrar(){
        label_round.setText(nCircuitos +"/"+actual.getNum_circuitos());
        label_exercice.setText(nEjercicios + "/" + actual.getNum_ejercicios());
    }
    
    private void reloadCheckGraphic(){
        //setting graphics selection checkbox
        ArrayList<Grupo> aGrupo = bbdd.getGym().getGrupos();
        ArrayList<String> al = new ArrayList();
        for(int i = 0; i < aGrupo.size(); i++ ){
            al.add(aGrupo.get(i).getCodigo());
        }
        select_group.setItems(FXCollections.observableList(al));
        combo_choice_group.setItems(FXCollections.observableList(al));
    }
    
    private void reloadCheckSesions(){
        //setting graphics selection checkbox
        ArrayList<SesionTipo> aSesion = bbdd.getGym().getTiposSesion();
        ArrayList<String> al = new ArrayList();
        for(int i = 0; i < aSesion.size(); i++ ){
            al.add(aSesion.get(i).getCodigo());
        }
        sesion_list_view.setItems(FXCollections.observableList(al));
        combo_choice_sesion.setItems(FXCollections.observableList(al));
    }


    @FXML
    private void showMenu(MouseEvent event) {
        clearPanels();
        main_panel.setVisible(true);
    }

    @FXML
    private void menuAction(ActionEvent event) {
        String button = ((Node)event.getSource()).getId();
        if(button.equals("exit_main_button") || button.equals("exit_button")){
            bbdd.salvar();
            Platform.exit();
        }else if(button.equals("group_button")){
            clearPanels();
            group_panel.setVisible(true);
        }else if(button.equals("sesion_button")){
            clearPanels();
            sesion_panel.setVisible(true);
        }else if(button.equals("home_menu")){
            clearPanels();
            main_panel.setVisible(true);
        }else if(button.equals("graphic_button")){
            clearPanels();
            reloadCheckGraphic();//recargo el contenido
            graphics_panel.setVisible(true);
        }
    }
    
    /**Sets visible false all panels**/
    private void clearPanels(){
        main_panel.setVisible(false);
        group_panel.setVisible(false);
        sesion_panel.setVisible(false);
        graphics_panel.setVisible(false);
    }

    @FXML
    private void group_actions(ActionEvent event) {
        String button = ((Node)event.getSource()).getId();
        if(button.equals("add_group")){
            //comprobamos que hay algo ahi
            boolean grupoRelleno = true;
            if(field_group_code.getText().trim().length() == 0){
                error_cod.setVisible(true);
                grupoRelleno = grupoRelleno && false;
            }else{
                error_cod.setVisible(false);
            }
            if(text_field_group_description.getText().isEmpty()){
                error_cod1.setVisible(true);
                grupoRelleno = grupoRelleno && false;
            }else{
                error_cod1.setVisible(false);
            }
            if(grupoRelleno){
                Grupo grupo = new Grupo();
                grupo.setCodigo(field_group_code.getText().trim());
                grupo.setDescripcion(text_field_group_description.getText());
                ch_group_o.add(grupo);//añadimos el grupo
                System.out.println("Grupo creado" + ch_group_o);
                //Limpieza campos
                field_group_code.setText("");
                text_field_group_description.setText("");
                reloadCheckGraphic();
            }else{
                System.out.println("Fallo");
            }
        }else if(button.equals("modify_group")){
            /*Grupo modificado = group_list_view.getSelectionModel().getSelectedItem();
            field_group_code.setText(modificado.getCodigo());
            text_field_group_description.setText(modificado.getDescripcion()); */  
        }else if(button.equals("save_group")){
            group_list_view.getSelectionModel().getSelectedItem().setCodigo(field_group_code.getText());
            group_list_view.getSelectionModel().getSelectedItem().setDescripcion(text_field_group_description.getText());
            group_list_view.refresh();
            reloadCheckGraphic();
            save_group.setDisable(true);
            add_group.setDisable(false);
            group_list_view.setDisable(false);
            //Limpieza campos
            field_group_code.setText("");
            text_field_group_description.setText("");
        }
    }
    /**Image view Actions**/
    @FXML
    private void goGroup(MouseEvent event) {
        clearPanels();
        group_panel.setVisible(true);
    }

    @FXML
    private void goSesion(MouseEvent event) {
        clearPanels();
        sesion_panel.setVisible(true);
    }

    @FXML
    private void goGraficas(MouseEvent event) {
        clearPanels();
        reloadCheckGraphic();//recargo el contenido
        graphics_panel.setVisible(true);
    }

    @FXML
    private void exit(MouseEvent event) {
        bbdd.salvar();
        Platform.exit();
    }
    /**Imane view Actions END**/
    
    class GroupCellCombo extends ListCell<Grupo> {
        HBox hbox = new HBox();//contenedor
        Label codigo = new Label("");
        public GroupCellCombo() {
            super();      
            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(codigo);
        }
        public void updateItem(Grupo name, boolean empty) {
            super.updateItem(name, empty);
            setText(null);
            setGraphic(null);
            if(name != null && !empty) {
                codigo.setText(name.getCodigo());  
                //combo_choice_group.setPromptText(name.getCodigo());
                setGraphic(hbox);
            }
        }   
    }
    
    /**Cell style de los parcieles de la declaracion de asignaturas**/
    class GroupCell extends ListCell<Grupo> {
        HBox hbox = new HBox();//contenedor
        Button modificar = new Button("Modificar");
        Label codigo = new Label("");
        Pane pane = new Pane();
        public GroupCell() {
            super();      
            hbox.setAlignment(Pos.CENTER);
            /*if(this.isSelected()){
                modificar.setDisable(false);
            }else{
                modificar.setDisable(true);
            }*/
            modificar.setOnAction(e -> {
                try{
                    if(this.isSelected()){
                        add_group.setDisable(true);//desactivo el boton añadir
                        save_group.setDisable(false);
                        Grupo modificado = group_list_view.getSelectionModel().getSelectedItem();
                        field_group_code.setText(modificado.getCodigo());
                        text_field_group_description.setText(modificado.getDescripcion());
                        group_list_view.setDisable(true);
                        System.out.println(group_list_view);
                        //seleccionar();
                    }else {System.out.println("Seleccionalo");}
                }catch(Exception a){ System.err.println("UPS parcial");}
            });
            modificar.getStyleClass().add("estilaco");
            hbox.getChildren().addAll(codigo,pane,modificar);
            hbox.setHgrow(pane, Priority.ALWAYS);
        }
        public void updateItem(Grupo name, boolean empty) {
            super.updateItem(name, empty);
            setText(null);
            setGraphic(null);
            if(name != null && !empty) {
                /*if(isSelected()){
                    modificar.setDisable(false);
                }else{
                    modificar.setDisable(true);
                }*/
                codigo.setText(name.getCodigo());             
                setGraphic(hbox);
            }
        }   
        private void seleccionar(){
            group_list_view.getSelectionModel().select(this.getItem());
        }
    }
    @FXML
    private void sesion_actions(ActionEvent event) {
        String button = ((Node)event.getSource()).getId();
        if(button.equals("save_sesion")){
            //comprobamos que hay algo ahi
            boolean grupoRelleno = true;
            if(field_sesion_code.getText().trim().length() == 0){
                error_codigo1.setVisible(true);
                grupoRelleno = grupoRelleno && false;
            }else{
                error_codigo1.setVisible(false);
            }
            //calentamiento 
            if(!Pattern.matches("[1-9]+",field_sesion_warm_up.getText())){
                error_calentamiento1.setVisible(true);
                grupoRelleno = grupoRelleno && false;
            }else{
                error_calentamiento1.setVisible(false);
            }
            //comprobacion de las numericas
            if(!Pattern.matches("[1-9]+", field_sesion_number_exercice.getText())){
               error_ejercicios1.setVisible(true);
               grupoRelleno = grupoRelleno && false;
            }else {
               error_ejercicios1.setVisible(false);
            }
            //comprobacion de las numericas
            if(!Pattern.matches("[1-9]+", field_sesion_time_exercice.getText())){
               error_t_ejercicios1.setVisible(true);
               grupoRelleno = grupoRelleno && false;
            }else {
               error_t_ejercicios1.setVisible(false);
            }
            
            //comprobacion de las numericas
            if(!Pattern.matches("[1-9]+", field_sesion_rest_exercice.getText())){
               error_des_ejercicio1.setVisible(true);
               grupoRelleno = grupoRelleno && false;
            }else {
               error_des_ejercicio1.setVisible(false);
            }
            
            //comprobacion de las numericas
            if(!Pattern.matches("[1-9]+", field_sesion_number_circuits.getText())){
               error_num_circuito1.setVisible(true);
               grupoRelleno = grupoRelleno && false;
            }else {
               error_num_circuito1.setVisible(false);
            }
            
            //comprobacion de las numericas
            if(!Pattern.matches("[1-9]+", field_sesion_rest_circuit.getText())){
               error_des_circuito1.setVisible(true);
               grupoRelleno = grupoRelleno && false;
            }else {
               error_des_circuito1.setVisible(false);
            }
            //proced
            if(grupoRelleno){
                SesionTipo sesion = new SesionTipo();
                sesion.setCodigo(field_sesion_code.getText());
                sesion.setT_calentamiento(Integer.parseInt(field_sesion_warm_up.getText().trim()));
                sesion.setNum_ejercicios(Integer.parseInt(field_sesion_number_exercice.getText()));
                sesion.setT_ejercicio(Integer.parseInt(field_sesion_time_exercice.getText()));
                sesion.setD_ejercicio(Integer.parseInt(field_sesion_rest_exercice.getText().trim()));
                sesion.setNum_circuitos(Integer.parseInt(field_sesion_number_circuits.getText().trim()));
                sesion.setD_circuito(Integer.parseInt(field_sesion_rest_circuit.getText()));
                ch_sesion_o.add(sesion);
                sesion_list_view.refresh();
                reloadCheckSesions();//actualizamos listas
                System.out.println("Guardando?: " + grupoRelleno);
            }else {
                System.out.println("Guardando?: " + grupoRelleno);
            }
        }
    }
    
    private void playSong(File file){
        Media sound = new Media(file.toURI().toString());
        MediaPlayer mediaPlayerS = new MediaPlayer(sound);
        mediaPlayerS.play();
    }
    
    class ClockTimer extends Task<Void>{
        void calcula() throws InterruptedException{
            Platform.runLater(() ->{
                type_label.setText(actual.getCodigo());
            });

            if(actual.getT_calentamiento() > 0){
                playSong(start);
                Thread.sleep(100);
                for(int c = tCalentamientoActual;c <= actual.getT_calentamiento();c++,tCalentamientoActual++){
                    Thread.sleep(1000);
                    label_clock.setText(c+"");
                    Platform.runLater(() -> {
                        label_step.setText("Calentamiento");
                        label_exercice.setText("-");
                        label_round.setText("-");
                    });
                }
            }

            for (int i = nCircuitos; i <= actual.getNum_circuitos(); i++, nCircuitos ++) {
                if (isCancelled()) {
                    break;
                }
                for(int j = nEjercicios; j <= actual.getNum_ejercicios(); j ++, nEjercicios++){
                    Platform.runLater(() -> {
                        playSong(start);
                        try {Thread.sleep(100);} catch (InterruptedException ex) {}
                        label_round.setText(nCircuitos +"/"+actual.getNum_circuitos());
                        label_exercice.setText(nEjercicios + "/" + actual.getNum_ejercicios());
                        label_step.setText("Ejercicio");
                    });

                    for(int k = tEjerciciosActual;k <= actual.getT_ejercicio();k++,tEjerciciosActual ++){
                        //mostrar();
                        Thread.sleep(1000);
                        System.out.println(""+k);
                        label_clock.setText(""+k);
                    }
                    Thread.sleep(100);
                    Platform.runLater(() -> {
                        playSong(finish);
                        label_step.setText("Descanso");
                    });
                    Thread.sleep(1000);
                    for(int l = dEjerciciosActual;l <= actual.getD_ejercicio();l++,dEjerciciosActual++){
                        //mostrar();
                        Thread.sleep(1000);
                        label_clock.setText(""+l);
                    }
                    tEjerciciosActual = 1;
                    dEjerciciosActual = 1;

                }
                //nEjercicios = 1;
                Platform.runLater(() -> {
                    playSong(finish);
                    label_step.setText("Descanso C");
                });
                Thread.sleep(100);

                for(int m = dCircuito;m <= actual.getD_circuito();m++,dCircuito++){
                    Thread.sleep(1000);
                    label_clock.setText(""+m);
                }
                nEjercicios = 1;
                Platform.runLater(() -> {
                    if(nCircuitos< actual.getNum_circuitos()){
                        label_round.setText(nCircuitos + "/" + actual.getNum_circuitos());
                    }
                });
                dCircuito = 1;
                System.out.println(">Circuito:" + nCircuitos);

            }

            //saving into sesion
            label_clock.setText("Final");
            combo_choice_sesion.setDisable(false);
            combo_choice_group.setDisable(false);
            Platform.runLater(()->{
                Duration d = Duration.ofMillis((System.currentTimeMillis()-horaInicio));
                Sesion sesion = new Sesion();
                sesion.setDuracion(d);
                sesion.setFecha(tiempoLocalIni);
                sesion.setTipo(actual);
                bbdd.getGym().getGrupos().get(combo_choice_group.getSelectionModel().getSelectedIndex()).getSesiones().add(sesion);//adding new session;
                bbdd.getGym().getGrupos().get(combo_choice_group.getSelectionModel().getSelectedIndex()).setDefaultTipoSesion(actual);
                //rebboting
                if(CronometroEjecutandose){
                    CronometroEjecutandose = false;
                    haFinalizado = true;
                    //resetting vars
                    tCalentamientoActual = 1;
                    nEjercicios = 1;
                    tEjerciciosActual = 1;
                    dEjerciciosActual = 1;
                    nCircuitos = 1;
                    dCircuito = 1;
                    play_button.setOpacity(1);
                    pause_button.setOpacity(0.6);
                    reboot_button.setOpacity(0.6);
                }
            });
            task.cancel(); 
            //reseteo todas las variables globales de tiempo 
        }

        @Override
        protected Void call() throws Exception {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    if (isCancelled()) {
                        break;
                    }
                }
                if (isCancelled()) {
                    break;
                }
                calcula();
            }
            return null;
        } 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //objeto auxiliar para el seguimiento de la actividad
    class Actividad{
        private int duracion; //t_ejercicio o d_ejercicio
        private int numEjercicio;
        private int tiempoEnCurso;
        
        public Actividad(int dur, int numEj, int tEnCurso){
            this.duracion = dur;
            this.numEjercicio = numEj;
            this.tiempoEnCurso = tEnCurso;
        }
        public int getTiempoEnCurso(){
            return this.tiempoEnCurso;
        }
        
        public int getNumEjercicio(){
            return this.numEjercicio;
        }
        
        public int getDuracion(){
            return this.duracion;
        }
        
        public void setTiempoEnCurso(int t){
            this.tiempoEnCurso = t;
        }
        
        public void setDuracion(int t){
            this.duracion = t;
        }
        
        public void setNumEjercicio(int t){
            this.numEjercicio = t;
        }
    }
}
