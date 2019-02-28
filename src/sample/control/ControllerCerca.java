package sample.control;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerCerca implements Initializable {
    @FXML AnchorPane paneButtons;

    int cantidadBotones;
    List<String> pathList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pathList = new ArrayList<>();
        JFXButton button;
        List<File> carpetaFiles = new ArrayList<>();
        File filePrueba = new File("src/sample/fxml/");
        System.out.println(filePrueba.list().length);
        try {
            carpetaFiles = Files.walk(Paths.get("src/sample/data"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cantidadBotones = 0;
        for (File file:carpetaFiles) {
            button = new JFXButton();
            //propiedades del botón.
            button.setId("button" + cantidadBotones++);
            button.setPrefWidth(100);
            button.setPrefHeight(100);
            button.setText(file.getName());
            button.setLayoutX(100*(cantidadBotones-1));

            //lista de rutas de los ficheros a los que hace referencia cada boton.
            pathList.add(file.getAbsolutePath());

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ControllerLlista.setCsvFile(pathList
                            .get(Integer
                                    .parseInt(((JFXButton)actionEvent
                                            .getSource())
                                            .getId()
                                            .replace("button", ""))));
                    /////////FALTA: CAMBIAR LA VENTANA AL HACER CLICK EN UN BOTON
                    System.out.println(((JFXButton)actionEvent.getSource()).getText());
                }
            });
            paneButtons.getChildren().add(button);
        }
    }
}
