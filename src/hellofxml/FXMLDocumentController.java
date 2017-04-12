/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author MonkeyAndres
 */
public class FXMLDocumentController implements Initializable {
	
	// Import Labels
	@FXML private Label labelNombre;
	@FXML private Label labelEdad;
	@FXML private Label labelOcupacion;
	
	
	// Imports inputs
	@FXML private TextField inputNombre;
	@FXML private TextField inputEdad;
	@FXML private TextField inputOcupacion;
	
	
	// Import Botones
	@FXML private Button btnEnviar;
	@FXML private Button btnLimpiar;
	@FXML private Button btnEliminar;
	@FXML private Button btnEditar;
	
	
	// Imports Table
	@FXML private TableView table;
	@FXML private TableColumn CNombre;
	@FXML private TableColumn CEdad;
	@FXML private TableColumn COcupacion;
	
	
	// Datos de la tabla
	private final ObservableList<Usuario> data = FXCollections.observableArrayList(); 
	
	
	@Override // Funcion init
	public void initialize(URL url, ResourceBundle rb) {
		table.setEditable(true);
		
		// Digo que cada columna ya hecha obtiene el valor que le he asignado.
		// nombreColumna.setCellValueFactory(new PropertyValueFactory("?"));
		// ? = Es el campo del objeto mandado que se va a visualizar en esa columna
		CNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
		CEdad.setCellValueFactory(new PropertyValueFactory("edad"));
		COcupacion.setCellValueFactory(new PropertyValueFactory("ocupacion"));
		
		table.setItems(data);
	}	
	
	
	@FXML // Evento click del boton enviar
	public void btnEnviarClick(ActionEvent e){
		Boolean allOkay = false;
		
		if(validar()) allOkay = true; // Valida que todos los campos estan OKAY
		else System.out.println("Falta algun campo"); // Si no estan Okay avisa por consola
		
		if(allOkay == true && seguro()){
			//Creo el usuario
			Usuario u = new Usuario();
			u.setNombre(inputNombre.getText());
			u.setEdad(Integer.parseInt(inputEdad.getText()));
			u.setOcupacion(inputOcupacion.getText());
			
			addToTable(u); // Lo agreo a el OBSEVABLELIST
			
			limpiarCampos(); // Limpio los campos
		}
	}
	
	
	@FXML // Evento click boton limpiar
	public void btnLimpiarClick(){
		data.clear();
	}
	
	
	@FXML // Evento click boton eliminar
	public void btnEliminarClick(ActionEvent e){
		Usuario selectedItem = (Usuario) table.getSelectionModel().getSelectedItem();
		table.getItems().remove(selectedItem);
	}
	
	
	@FXML //Evento click boton editar
	public void btnEditarClick(ActionEvent e){
		Usuario u = (Usuario) table.getSelectionModel().getSelectedItem();
		
		int indexUsuario = buscarUsuario(u);
		
		inputNombre.setText(u.getNombre());
		inputEdad.setText(String.valueOf(u.getEdad()));
		inputOcupacion.setText(u.getOcupacion());
		
		data.remove(indexUsuario);
	}
	
	// Metodo que valida y lanza un aviso
	private Boolean validar(){
		
		//Convierto la cadena a INT si me salta una excepcion es que no es un numero
		Boolean edadOkay = true;
		try{
			int edad = Integer.parseInt(inputEdad.getText());
		} catch (NumberFormatException e){
			edadOkay = false;
		}
	
		
		if(!inputNombre.getText().trim().isEmpty() && !inputOcupacion.getText().trim().isEmpty() && !inputEdad.getText().trim().isEmpty() && edadOkay){
			return true;
		} else {			
			Alert alert = new Alert(AlertType.ERROR); //Crea una alerta de tipo ERROR
			alert.setTitle("Error al validar");
			alert.setContentText("Por favor vuelve a revisarlo :)");
			
			// Establece un titulo dinamico dependiendo de el campo vacio
			if (inputNombre.getText().trim().isEmpty()) alert.setHeaderText("Te falta el nombre");
			else if (inputEdad.getText().trim().isEmpty()) alert.setHeaderText("Te falta la edad");
			else if (inputOcupacion.getText().trim().isEmpty()) alert.setHeaderText("Te falta la ocupacion");
			else if (!edadOkay) alert.setHeaderText("La edad no es un numero");
			
			
			alert.showAndWait(); // Muestra la alerta
			
			return false;
		}
	}
	
	
	// Metodo que lanza una alerta de confirmacion
	private Boolean seguro(){
		Alert alert = new Alert(AlertType.CONFIRMATION); //Crea una alerta de tipo confirmacion
		
		//Aniade diferentes titulos
		alert.setTitle("Confirmar Enviar");
		alert.setHeaderText("Confirmar Registro de "+inputNombre.getText());
		alert.setContentText("Vas a registrar un usuario. Estas Seguro?");
		
		//Crea dos botones uno de tipo cancelar
		ButtonType buttonTypeYes = new ButtonType("Yes");
		ButtonType buttonTypeNo = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		//Mete los botones en la alert
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

		Boolean r = false; //Variable de retorno

		//Evalua el resultado
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonTypeYes){
			r = true; //Si se pulsa el buttonTypeYes r = true
		} else if(result.get() == buttonTypeNo){
			System.out.println("No se ha enviado");
			r = false; //Si se pulsa el buttonTypeNo r = false
		}
		
		return r;
	}
	
	
	// Agrega el usuario a la tabla y a una DB (WIP)
	private void addToTable(Usuario u){
		data.add(u);
	}
	
	// Limpio los campos de la tabla
	private void limpiarCampos(){
		inputNombre.clear();
		inputEdad.clear();
		inputOcupacion.clear();
	}
	
	// Devuelve de index de un usuario
	private int buscarUsuario(Usuario u){
		int valor = 0;
		
		try{
			for(int i = 0; data.get(i) != null; i++){
				if(data.get(i).getNombre() == u.getNombre() && data.get(i).getEdad()== u.getEdad() && data.get(i).getOcupacion()== u.getOcupacion()){
					valor = i;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("No hay mas elementos");
		}
		
		return valor;
	} 
}
