package br.com.atfl.comunicacaotcp.run;

import br.com.atfl.comunicacaotcp.server.ServidorTCP;
import com.google.common.eventbus.EventBus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    private ServidorTCP servidorTCP; 
    @FXML
    private Label label;
    @FXML 
    TextField portaText;
    @FXML
    Button buttonStop;
    @FXML
    Button buttonInit;
    @FXML
    Button buttonUsuarios;
    @FXML 
    Button closeButton;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            String p = portaText.getText();
            int porta = Integer.parseInt(p);
            startServer(porta);
            label.setText("Server is running...");
            buttonStop.setDisable(false);
            buttonUsuarios.setDisable(false);
            buttonInit.setDisable(true);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Porta inválida");
            alert.setContentText("Informe uma porta válida.");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleButtonStopAction(ActionEvent event) {
        label.setText("Stopped");
        if(servidorTCP!=null){
            this.stopServer();
        }
    }    

    @FXML
    private void handleButtonUserAction(ActionEvent event) {
            Stage stage = new Stage();
            UsuarioView v = new UsuarioView();
            v.start(stage);
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        buttonStop.setDisable(true);
        buttonUsuarios.setDisable(true);
        portaText.requestFocus();
    }    
    
    private void startServer(int porta){
        EventBus eventBus = new EventBus();
        servidorTCP  = new ServidorTCP(porta, eventBus);
        servidorTCP.start();
    }
    
    private void stopServer(){
        servidorTCP.stop();
        buttonStop.setDisable(true);
        buttonUsuarios.setDisable(true);
        buttonInit.setDisable(false);
    }
    
    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
