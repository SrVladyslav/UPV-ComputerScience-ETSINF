/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega2ipcfitness;

import accesoBD.AccesoBD;
import static accesoBD.AccesoBD.getInstance;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;

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
    
    private long nowTime;
    
    private boolean menuActive = false;
    @FXML
    private Button exit_main_button;
    @FXML
    private ComboBox<Grupo> combo_choice_group;
    private ObservableList<Grupo> ch_group_o;
    @FXML
    private ComboBox<Sesion> combo_choice_sesion;
    private ObservableList<Sesion> ch_sesion_o;
    
    
    private AccesoBD bbdd;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //creating gym :P
        bbdd = getInstance();
        menuActive = false;
        combo_choice_group = new ComboBox<Grupo>();
        ArrayList<Grupo> ch_group_a = bbdd.getGym().getGrupos();
        
        //creating sessions :)
        combo_choice_sesion = new ComboBox<Sesion>();
        
        
        ch_group_o = FXCollections.observableList(ch_group_a);
       // combo_choice_group.setItems();
    }    

    @FXML
    private void controlTime(MouseEvent event) {
        String button = ((Node)event.getSource()).getId();
        if(button.equals("play_button")){
            
        }
    }

    @FXML
    private void showMenu(MouseEvent event) {
        if(menuActive){
            menu_panel.setVisible(false);
            menuActive = false;
        }else{
            menu_panel.setVisible(true);
            menuActive = true;
        }
    }

    @FXML
    private void menuAction(ActionEvent event) {
        String button = ((Node)event.getSource()).getId();
        if(button.equals("exit_main_button") || button.equals("exit_button")){
            Platform.exit();
        }
    }
    
}
