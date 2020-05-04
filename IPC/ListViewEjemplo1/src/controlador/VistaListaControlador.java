package controlador;

import vista.VistaModificarController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import modelo.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vista.VistaAnyadirController;

public class VistaListaControlador implements Initializable {
	
	
    @FXML private ListView<Persona> vistadeListafxID;
    //@FXML private ListView<String> vistadeListafxID;
    @FXML private Button BAddfxID;
    @FXML private Button BBorrarfxID;
    @FXML
    private Button modificar;
    
    @FXML void addAccion(ActionEvent event) throws IOException {
        // añade a la colección si los campos no son vacíos y no contienen únicamente blancos
        anyadi(event);
    }

    @FXML void borrarAccion(ActionEvent event) {
    	int i = vistadeListafxID.getSelectionModel().getSelectedIndex();
    	if (i>=0) datos.remove(i);
    }
	
    private ObservableList<Persona> datos = null; // Colecci�n vinculada a la vista.

    public ListView getSelectionModel(){
        return vistadeListafxID;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
            // TODO Auto-generated method stub
            ArrayList<Persona> misdatos = new ArrayList<Persona>();
            misdatos.add(new Persona("Pepe", "García"));
            misdatos.add(new Persona("María", "Pérez"));
            datos = FXCollections.observableArrayList(misdatos);
            vistadeListafxID.setItems(datos); // vinculaci�n entre la vista y el modelo



            // inhabilitar botones al arrancar.
            BAddfxID.setDisable(false);
            BBorrarfxID.setDisable(true);
            modificar.setDisable(true);
            // oyente de foco para el textfield.


            // oyente de foco para el listView
            vistadeListafxID.focusedProperty().addListener(new ChangeListener<Boolean>()
            {	@Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                    Boolean newValue) {
                            if (vistadeListafxID.isFocused()) {
                                    BBorrarfxID.setDisable(false);
                                    BAddfxID.setDisable(false);
                                    modificar.setDisable(false);
                            }

                    }

            });
    }

    //VENTANA MODIFICAR
    @FXML
    private void modifi(ActionEvent event) throws IOException {
        //cargo el controlador de la ventana modificar
        System.out.print("entra al modificar omg");
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/vista/vistaModificar.fxml"));
            GridPane root = (GridPane) cargador.load();
            //accedo al controlador de datos de la persona
            VistaModificarController controladorPersona = 
                    cargador.<VistaModificarController>getController();
            //detecto seleccion
            int indice = vistadeListafxID.getSelectionModel().getSelectedIndex();
            if ( indice >= 0) { controladorPersona.initDatosPersona(datos.get(indice));
            }else return;
            
            Scene scene = new Scene(root, 300, 200);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);//la ventana es modal
            stage.setTitle("MOdificar datos persona");
            stage.showAndWait();
            if(!controladorPersona.getCancelar()) //si no se pulso cancelar
            {
                Persona p = controladorPersona.getPersona();
                datos.set(indice, p);
                //
            }
        }catch(Exception e){
            System.err.println("Algo funciona mal");
            System.out.println(e.toString());
        }; 
    }
    
    //VENTANA AÑADIR
    @FXML
    private void anyadi(ActionEvent event) throws IOException {
        //cargo el controlador de la ventana modificar
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/vista/vistaAnyadir.fxml"));
            GridPane root = (GridPane) cargador.load();
            //accedo al controlador de datos de la persona
            VistaAnyadirController controladorPersona = 
                    cargador.<VistaAnyadirController>getController();
            
            Scene scene = new Scene(root, 300, 200);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);//la ventana es modal
            stage.setTitle("Añadir persona");
            stage.showAndWait();
            if(!controladorPersona.getCancelar()) //si no se pulso cancelar
            {
                Persona p = controladorPersona.getPersona();
                if(p != null){
                    datos.add(p);
                }
            }
        }catch(Exception e){
            System.err.println("Algo funciona mal");
            System.out.println(e.toString());
        }; 
    }
}
