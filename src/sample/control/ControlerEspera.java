package sample.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.model.Hospital;
import sample.model.Pacient;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControlerEspera implements Initializable {


    @FXML TableView<Pacient> tableListaEspera;

    private List<Pacient> pacientList = new ArrayList<>();
    private ObservableList<Pacient> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = FXCollections.observableArrayList();
        loadData();
        if(pacientList.size() != 0) {
            setTableView();
        }
    }

    private void loadData() {
        Hospital hospital = new Hospital();
        pacientList.addAll(hospital.loadPacients("src/sample/data/LlistaEspera.csv"));
    }

    private void setTableView() {
        data.clear();

        TableColumn DNI = new TableColumn("DNI");
        TableColumn Nom = new TableColumn("Nom");
        TableColumn Cognoms = new TableColumn("Cognoms");
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        TableColumn Genre = new TableColumn("Gènere");
        TableColumn Telefon = new TableColumn("Telèfon");
        TableColumn pes = new TableColumn("Pes");
        TableColumn Alçada = new TableColumn("Alçada");

        // COMPTE!!!! les propietats han de tenir getters i setters
        DNI.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DNI"));
        Nom.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Nom"));
        Cognoms.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Cognoms"));
        DataNaix.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DataNaixament"));
        Genre.setCellValueFactory(new PropertyValueFactory<Pacient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Pacient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Pacient, Integer>("Alçada"));

        tableListaEspera.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);

        //data.add(new Pacient("111", "n", "co", LocalDate.of(2000, 12, 12), Persona.Genere.HOME, "55555", 5.4f, 100));
        data.addAll(pacientList);
        tableListaEspera.setItems(data);

    }
    public void clickTable(MouseEvent event) {
        //Cal verificar si hi ha alguna selecció feta al fer doble click

        try {
            if (pacientList != null && tableListaEspera != null && event != null) {

                if (event.getClickCount() == 2 && !tableListaEspera.getSelectionModel().isEmpty()) {

                    if (pacientList.remove(tableListaEspera.getSelectionModel().getSelectedItem())) {

                        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/sample/data/LlistaEspera.csv")));
                        bw.write("dni,nom,cognoms,datanaixament,genere,telèfon,pes,alçada");

                        for (Pacient p : pacientList) {
                            bw.newLine();
                            bw.write(p.getDNI() + ","
                                    + p.getNom() + ","
                                    + p.getCognoms() + ","
                                    + ((p.getDataNaixament().getDayOfMonth()<10)
                                    ? "0" + p.getDataNaixament().getDayOfMonth()
                                    : p.getDataNaixament().getDayOfMonth()) + "/"
                                    + ((p.getDataNaixament().getMonthValue()<10)
                                    ? "0" + p.getDataNaixament().getMonthValue()
                                    : p.getDataNaixament().getMonthValue()) + "/"
                                    + p.getDataNaixament().getYear() + ","
                                    + p.getGenere() + ","
                                    + p.getTelefon() + ","
                                    + "\"" + p.getPes()  + "\","
                                    + "\"" + p.getAlçada() +  "\"");
                        }
                        bw.close();
                        setTableView();
                        // Actualizar CSV
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// FALTA: - Añadir comillas csv. - Salto de linea.
