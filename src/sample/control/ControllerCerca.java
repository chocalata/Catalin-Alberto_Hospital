package sample.control;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

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

    int columnaBotones;
    int filaBotones;

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
        filaBotones = 0;
        columnaBotones = 0;
        for (File file:carpetaFiles) {
            if (new File(file.getName()).getName().matches("^.*\\.csv$")) {
                button = new JFXButton();
                //propiedades del botón.
                button.setPrefWidth(100);
                button.setPrefHeight(100);
                button.setText(file.getName());
                button.setLayoutX(100 * (columnaBotones));
                button.setLayoutY(100 * (filaBotones));
                button.setId("button" + ((filaBotones*7) + columnaBotones++));

                if(columnaBotones == 7){
                    filaBotones++;
                    columnaBotones = 0;
                }
                //lista de rutas de los ficheros a los que hace referencia cada boton.
                pathList.add(file.getAbsolutePath());

                button.setOnAction(actionEvent -> {
                    ControllerLlista.setCsvFile(pathList
                            .get(Integer
                                    .parseInt(((JFXButton) actionEvent
                                            .getSource())
                                            .getId()
                                            .replace("button", ""))));

                    try {
                        Controller.paneDret.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/llista.fxml")));
                        System.out.println(((JFXButton) actionEvent.getSource()).getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    });
                paneButtons.getChildren().add(button);
            }
        }
    }
}
