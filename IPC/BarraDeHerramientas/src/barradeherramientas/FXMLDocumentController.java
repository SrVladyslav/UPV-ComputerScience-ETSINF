/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barradeherramientas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author vlama
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private MenuItem button_Salir;
    @FXML
    private CheckMenuItem menuAmazon;
    @FXML
    private CheckMenuItem menuEbay;
    @FXML
    private ImageView button_Amazon;
    @FXML
    private ImageView button_Blogger;
    @FXML
    private ImageView button_Ebay;
    @FXML
    private ImageView button_Facebook;
    @FXML
    private ImageView button_Google;
    @FXML
    private Label label_Estado;
    @FXML
    private Button boton_Amazon;
    @FXML
    private Button button_bloger;
    @FXML
    private Button button_facebook;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu1;
    @FXML
    private Menu menu2;
    @FXML
    private Menu menu3;
    @FXML
    private ToolBar toolBar;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        menuBar.setStyle("-fx-background-color: #D8D8D8");
        toolBar.setStyle("-fx-background-color: #C1C1C1");
    }    

    @FXML
    private void salir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Vas a salir del programa?");
        alert.setContentText("¿Seguro que quieres salir?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("Cerrando");
            Platform.exit();
        }
    }


    @FXML
    private void mEbay(ActionEvent event) {
        showWindowAlert(menuEbay, "Ebay");
    }

    @FXML
    private void mAmazon(ActionEvent event) {
        showWindowAlert(menuAmazon, "Amazon");
    }
    
    
    /**Shows the window to Amazon or Ebay menu selected**/
    private void showWindowAlert(CheckMenuItem a, String str){
        if(a.isSelected()){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Compra realizada correctamente!");
            alert.setContentText("He comprado en "+str+"!");
            
            ButtonType buttonOk = new ButtonType("OK");
            alert.getButtonTypes().setAll(buttonOk);
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == buttonOk){
                System.out.println("Cerrando");
            }
        }else {
             Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de la seleccion");
            alert.setHeaderText("No puede comprar en "+str+".");
            alert.setContentText("Por favor, cambie la seleccion actual en el menu de opciones.");
            
            ButtonType buttonOk = new ButtonType("OK");
            alert.getButtonTypes().setAll(buttonOk);
            
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == buttonOk){
                System.out.println("Cerrando");
            }
        }
    }

    @FXML
    private void mBlogger(ActionEvent event) {
        List<String> blog = new ArrayList<>();
        blog.add("El blog de Arthos");
        blog.add("El blog de Porthos");
        blog.add("El blog de Aramis");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("El blog de Arthos", blog);
        dialog.setTitle("Selecciona un blog");
        dialog.setHeaderText("¿Que blog quieres visitar?");
        dialog.setContentText("Elige:");
        Optional<String> result = dialog.showAndWait();
        // Pre Java 8
        if (result.isPresent()) {
           label_Estado.setText("Visitando " + result.get());
        }else{
            label_Estado.setText("");
        }

    }

    @FXML
    private void mFacebook(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(""); // Por defecto
        dialog.setTitle("Introduce tu nombre");
        dialog.setHeaderText("¿Con qué usuario quieres escribir en Facebook?");
        dialog.setContentText("Introduce tu nombre:");
        Optional<String> result = dialog.showAndWait();
        // Obteniendo el resultado (pre Java 8)
        if (result.isPresent() && !result.get().equals("")) {
           label_Estado.setText("Mensaje enviado como " + result.get());
        }else{
            label_Estado.setText("");
        }
    }
}
