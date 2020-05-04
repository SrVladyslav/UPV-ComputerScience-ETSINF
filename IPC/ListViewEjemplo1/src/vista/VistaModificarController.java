/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author vlama
 */
public class VistaModificarController implements Initializable {
    private boolean pulsadoCancelar;
    private Persona personaNueva;
    @FXML
    private Button button_salvar;
    @FXML
    private Button button_cancelar;
    @FXML
    private TextField textFieldfxID;
    @FXML
    private TextField textFieldApellidosfxID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsadoCancelar(ActionEvent event) {
        pulsadoCancelar = true;
        ((Button)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void pulsadoSalvar(ActionEvent event) {
        personaNueva = new Persona(textFieldfxID.getText(),textFieldApellidosfxID.getText());
        ((Button)event.getSource()).getScene().getWindow().hide();
    }
    
    /**
     @return nboolean: pulsado cancelar**/
    public boolean getCancelar(){
        return pulsadoCancelar;
    }
    
    public void initDatosPersona(Persona p){
        textFieldfxID.setText(p.getNombre());
        textFieldApellidosfxID.setText(p.getApellidos());
        personaNueva = new Persona(p.getNombre(), p.getApellidos());
    }
    
    /**
     @return Persona: persona modificada**/
    public final Persona getPersona(){
        return personaNueva;
    }
}
