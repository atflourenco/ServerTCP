/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atfl.comunicacaotcp.run;


import br.com.atfl.comunicacaotcp.model.Usuario;
import br.com.atfl.comunicacaotcp.server.ServidorTCP;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Anderson
 */
public class UsuarioView extends Application {
    
    private Map<String, Usuario> usuariosOnLine = ServidorTCP.getUsuariosOnLine();

	private TableView<br.com.atfl.comunicacaotcp.beans.Usuario> table;
	private ObservableList<br.com.atfl.comunicacaotcp.beans.Usuario> data;
	private Text actionStatus;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Lista de usuários");

		// Books label
		Label label = new Label("Usuários online");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Table view, data, columns and properties
		table = new TableView<>();
		data = getInitialTableData();
		table.setItems(data);

		TableColumn titleCol = new TableColumn("IP");
		titleCol.setCellValueFactory(new PropertyValueFactory<br.com.atfl.comunicacaotcp.beans.Usuario, String>("ip"));
		TableColumn authorCol = new TableColumn("Nome");
		authorCol.setCellValueFactory(new PropertyValueFactory<br.com.atfl.comunicacaotcp.beans.Usuario, String>("nome"));

		table.getColumns().setAll(titleCol, authorCol);
		table.setPrefWidth(450);
		table.setPrefHeight(500);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedIndexProperty().addListener(
				new RowSelectChangeListener());
                

		// Status message text
		actionStatus = new Text();
		actionStatus.setFill(Color.FIREBRICK);

		// Vbox
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, table, actionStatus);

		// Scene
		Scene scene = new Scene(vbox, 500, 475); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		// Select the first row
		table.getSelectionModel().select(0);
		br.com.atfl.comunicacaotcp.beans.Usuario usuario = table.getSelectionModel().getSelectedItem();
                if(usuario!=null){
                    actionStatus.setText(usuario.toString());
                }
		
	} // start()

	private class RowSelectChangeListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> ov, 
				Number oldVal, Number newVal) {

			int ix = newVal.intValue();

			if ((ix < 0) || (ix >= data.size())) {
	
				return; // invalid data
			}

			br.com.atfl.comunicacaotcp.beans.Usuario usuario = data.get(ix);
			actionStatus.setText(usuario.toString());	
		}
	}
	
	private ObservableList<br.com.atfl.comunicacaotcp.beans.Usuario> getInitialTableData() {
		
		List<br.com.atfl.comunicacaotcp.beans.Usuario> list = new ArrayList<>();
		 
                for(Usuario u: usuariosOnLine.values()){
                    list.add(new br.com.atfl.comunicacaotcp.beans.Usuario(u.getIp(),u.getNome()));
                }
                
//                for(int i=0;i<25;i++){
//                    list.add(new br.com.atfl.comunicacaotcp.beans.Usuario("192.168.0."+i, "Usuário " + i));
//                }
                
		ObservableList<br.com.atfl.comunicacaotcp.beans.Usuario> data = FXCollections.observableList(list);

		return data;
	}    
    
}
