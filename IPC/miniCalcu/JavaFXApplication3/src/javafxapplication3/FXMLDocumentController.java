/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 *
 * @author vlama
 */
public class FXMLDocumentController implements Initializable {
    private boolean label_activo = false;
    private double solucion = -20;
    
    private Label label;
    @FXML
    private Text resultado;
    @FXML
    private Button button_1;
    @FXML
    private Button button_10;
    @FXML
    private Button button_5;
    @FXML
    private CheckBox button_restar;
    @FXML
    private Label label_solu;
    @FXML
    private Button button_suma;
    @FXML
    private TextField text_valor;
    
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void resta(ActionEvent event) {
        if(label_activo) {
            label_solu.setText("");
            label_activo = false;
        }else{
            label_solu.setText("¡Estas restando!");
            label_activo = true;
        }
    }

    @FXML
    private void button_operations(ActionEvent event) {
        String id = ((Node)event.getSource()).getId();
        if(label_activo){
            //resta activa
            switch(id){
                case "button_1":{
                        solucion -= 1;
                        resultado.setText(solucion+"");break;}
                case "button_5":{ 
                        solucion -= 5;
                        resultado.setText(solucion+"");break;}
                case "button_10":{ 
                        solucion -= 10;
                        resultado.setText(solucion+"");break;}
                case "button_suma":{ 
                        solucion -= Integer.parseInt(text_valor.getText());
                        resultado.setText(solucion+"");break;}
                default:break;
            }
        }else {
            //resta desactivada
            switch(id){
                case "button_1":{
                        solucion += 1;
                        resultado.setText(solucion+"");break;}
                case "button_5":{ 
                        solucion += 5;
                        resultado.setText(solucion+"");break;}
                case "button_10":{ 
                        solucion += 10;
                        resultado.setText(solucion+"");break;}
                case "button_suma":{ 
                        solucion += Integer.parseInt(text_valor.getText());
                        resultado.setText(solucion+"");break;}
                default:break;
            }
        }
    }

    @FXML
    private void key_operation(KeyEvent event) {
        
        String id = event.getText();
        if(label_activo){
            //resta activa
            switch(id){
                case "1":{
                        solucion -= 1;
                        resultado.setText(solucion+"");break;}
                case "5":{ 
                        solucion -= 5;
                        resultado.setText(solucion+"");break;}
                case "x": case "X":{ 
                        solucion -= 10;
                        resultado.setText(solucion+"");break;}
                default:break;
            }
        }else {
            //resta desactivada
            switch(id){
                case "1":{
                        solucion += 1;
                        resultado.setText(solucion+"");break;}
                case "5":{ 
                        solucion += 5;
                        resultado.setText(solucion+"");break;}
                case "x":case "X":{ 
                        solucion += 10;
                        resultado.setText(solucion+"");break;}
                default:break;
            }
        }
    }
    
}
