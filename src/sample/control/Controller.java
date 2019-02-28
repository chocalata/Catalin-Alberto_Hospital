package sample.control;

//import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public String csvFile = null; //"src/hospital/data/Hospital.csv";
    @FXML AnchorPane paneDretORG;

    static AnchorPane paneDret;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneDret = paneDretORG;
    }

    @FXML public void onClickBtnMenu(ActionEvent actionEvent) {
        //System.out.println( ((Button)actionEvent.getSource()).getText());
        String btn = ((Button)actionEvent.getSource()).getId();
        AnchorPane anchorPaneLlista = null;
        paneDretORG.getChildren().clear();
        try {
            if(btn.equals("btnLlista")) {
                // ListView lsvLlista = new ListView();
                    anchorPaneLlista = FXMLLoader.load(getClass().getResource("../fxml/llista.fxml"));
            }else if(btn.equals("btnCerca")){
                    anchorPaneLlista = FXMLLoader.load(getClass().getResource("../fxml/cerca.fxml"));
            }else if( btn.equals("btnLlistaEspera")){
                    anchorPaneLlista = FXMLLoader.load(getClass().getResource("../fxml/llistaEspera.fxml"));
            }else {paneDretORG.getChildren().clear();}
            paneDretORG.getChildren().add(anchorPaneLlista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}