/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author vlama
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private GridPane panel_principal;
    @FXML
    private Text solution_label;
    @FXML
    private Button button_show;
    @FXML
    private Button button_stop;
    
    private Time time;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void handdle_show(ActionEvent event) {
        time = new Time();
        solution_label.textProperty().bind(time.messageProperty());
        Thread th = new Thread(time);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    private void hanndle_stop(ActionEvent event) {
        time.cancel();
    }    
}

class Time extends Task<Void> {
    private String time;
    
    @Override
    protected Void call() throws Exception {
        while (true)
        {
            time = LocalTime.now().format(DateTimeFormatter.ISO_TIME);
            System.out.println("hola"+ time);
            //updateValue();
            /*Platform.runLater(() -> {
            solution_label.setText(miTarea.valueProperty().get().toString());
            });*/
            
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                if(isCancelled()) break;
            }
        }
        return null;
    }
    
}
