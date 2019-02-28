package sample.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import sample.model.Hospital;
import sample.model.Pacient;
import sample.model.Persona;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerLlista implements Initializable {

    private static String csvFile = null;
    private List<Pacient> pacientListORG = new ArrayList<>();
    private List<Pacient> pacientList = new ArrayList<>();


    private ObservableList<Pacient> data;

    @FXML TableView<Pacient> tablePacients;
    @FXML JFXButton btnLoadFile;
    @FXML JFXTextField txtDNI, txtNom, txtCognoms, txtEdat, txtAlcada, txtPes;
    @FXML PieChart idPieChart, idPieChart1, idPieChart2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = FXCollections.observableArrayList();
        if(csvFile == null) {
            btnLoadFile.setText("Click per carregar CSV");
        }else {
            setTableView();
        }
    }

    private void setTableView() {
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

        tablePacients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);


        //data.add(new Pacient("111", "n", "co", LocalDate.of(2000, 12, 12), Persona.Genere.HOME, "55555", 5.4f, 100));
        loadData();
        data.addAll(pacientList);
        tablePacients.setItems(data);

    }

    private void loadData() {
        Hospital hospital = new Hospital();
        pacientList.addAll(hospital.loadPacients(csvFile));
    }


    public void clickLoadFile() {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if(file != null) {
            csvFile = file.getAbsolutePath();
            setTableView();
        }
    }

    private boolean filtroEdad(Pacient pacient){
        if(txtEdat.getText().matches("^[0-9]+$")){
            return pacient.getEdat() == Integer.parseInt(txtEdat.getText());
        }else if(txtEdat.getText().matches("^[0-9]+-[0-9]+$")){
            int num1 = Integer.parseInt(txtEdat.getText().split("-")[0]);
            int num2 = Integer.parseInt(txtEdat.getText().split("-")[1]);
            return num1 > num2
                    ?pacient.getEdat()<=num1 && pacient.getEdat()>=num2
                    :pacient.getEdat()>=num1 && pacient.getEdat()<=num2;
        }else if(txtEdat.getText().matches("^[0-9]+([<>])$")){
            if(txtEdat.getText().replace(">", "").matches("^[0-9]+$")){
                return Integer.parseInt(txtEdat.getText().replace(">","")) > pacient.getEdat();
            }else {
                return Integer.parseInt(txtEdat.getText().replace("<","")) < pacient.getEdat();
            }
        }else {
            return false;
        }
    }
    private boolean filtroAltura(Pacient pacient){
        if(txtAlcada.getText().matches("^[0-9]+$")){
            return pacient.getAlçada() == Integer.parseInt(txtAlcada.getText());
        }else if(txtAlcada.getText().matches("^[0-9]+-[0-9]+$")){
            int num1 = Integer.parseInt(txtAlcada.getText().split("-")[0]);
            int num2 = Integer.parseInt(txtAlcada.getText().split("-")[1]);
            return num1 > num2
                    ?pacient.getAlçada()<=num1 && pacient.getAlçada()>=num2
                    :pacient.getAlçada()>=num1 && pacient.getAlçada()<=num2;
        }else if(txtAlcada.getText().matches("^[0-9]+([<>])$")){
            if(txtAlcada.getText().replace(">", "").matches("^[0-9]+$")){
                return Integer.parseInt(txtAlcada.getText().replace(">","")) > pacient.getAlçada();
            }else {
                return Integer.parseInt(txtAlcada.getText().replace("<","")) < pacient.getAlçada();
            }
        }else {
            return false;
        }
    }
    private boolean filtroPeso(Pacient pacient){
        if(txtPes.getText().matches("^[0-9]+(\\.[0-9]+)?$")){
            return pacient.getPes() == Float.parseFloat(txtPes.getText());
        }else if(txtPes.getText().matches("^[0-9]+(\\.[0-9]+)?-[0-9]+(\\.[0-9]+)?$")){
            float num1 = Float.parseFloat(txtPes.getText().split("-")[0]);
            float num2 = Float.parseFloat(txtPes.getText().split("-")[1]);
            return num1 > num2
                    ?pacient.getPes()<=num1 && pacient.getPes()>=num2
                    :pacient.getPes()>=num1 && pacient.getPes()<=num2;
        }else if(txtPes.getText().matches("^[0-9]+(\\.[0-9]+)?([<>])$")){
            if(txtPes.getText().replace(">", "").matches("^[0-9]+(\\.[0-9]+)?$")){
                return Float.parseFloat(txtPes.getText().replace(">","")) > pacient.getPes();
            }else {
                return Float.parseFloat(txtPes.getText().replace("<","")) < pacient.getPes();
            }
        }else {
            return false;
        }
    }
    public void btnCerca(ActionEvent event) {
//        if(pacientList.get(0).getEdat() == Integer.parseInt(txtEdat.getText())) {
//            System.out.println("TIENEN LA MISMA EDAD");
//        }
        List<Pacient> pacients = pacientList.stream()
                .filter(pacient -> txtNom.getText().equals("") || pacient.getNom().contains(txtNom.getText()))
                .filter((pacient -> txtCognoms.getText().equals("") || pacient.getCognoms().contains(txtCognoms.getText())))
                .filter(pacient -> txtDNI.getText().equals("") || pacient.getDNI().contains(txtDNI.getText()))
                .filter(pacient -> txtEdat.getText().equals("") || filtroEdad(pacient))
                .filter(pacient -> txtAlcada.getText().equals("") || filtroAltura(pacient))
                .filter(pacient -> txtPes.getText().equals("") || filtroPeso(pacient))
                /*{
                    if(txtDNI.getText().isEmpty() && !txtEdat.getText().isEmpty()
                            || txtEdat.getText().isEmpty() && !txtDNI.getText().isEmpty()){
                        if(txtDNI.getText().isEmpty()){
                            return filtroEdad(pacient);
                            //pacient.getEdat() == Integer.parseInt(txtEdat.getText());
                        }else {
                            return pacient.getDNI().equals(txtDNI.getText());
                        }
                    }else if(!txtDNI.getText().isEmpty() && !txtEdat.getText().isEmpty()){
                        return pacient.getDNI().equals(txtDNI.getText()) && filtroEdad(pacient);
                    }else {
                        return false;
                    }
                }*/
                .collect(Collectors.toList());

        if(txtNom.getText().equals("")
                && txtCognoms.getText().equals("")
                && txtDNI.getText().equals("")
                && txtEdat.getText().equals("")
                && txtAlcada.getText().equals("")
                && txtPes.getText().equals("")) {
            updateTable(pacientList);
        }else updateTable(pacients);
    }

    private void updateTable(List<Pacient> pacients) {
        data.clear();
        data.addAll(pacients);
        tablePacients.setItems(data);
    }
///////////////////FALTA: que se pueda buscar con el tipo de filtro de changeText() y con btnCerca()
    /*public void changeText(KeyEvent keyEvent) {
        data.clear();
        List<Pacient> pacients = pacientList.stream()
                .filter(pacient -> pacient.getNom().contains(txtNom.getText()))
                .filter((pacient -> pacient.getCognoms().contains(txtCognoms.getText())))
                .filter(pacient -> pacient.getDNI().contains(txtDNI.getText()))
                .collect(Collectors.toList());
        data.addAll(pacients);
        tablePacients.setItems(data);
    }*/

    public void clickTable(MouseEvent event) {
        //Cal verificar si hi ha alguna selecció feta al fer doble click
        if (event.getClickCount() == 2 && !tablePacients.getSelectionModel().isEmpty()){
            System.out.println(tablePacients.getSelectionModel().getSelectedItem().getNom());
        }
           // Crear Metodo crear Tabla lista de espera (utilizar unicamente si no esta instanciado la lista).
          // Crear metodo añadir persona a la Tabla (Nombre, apellido)
         // Mostrar la tabla en la pestaña llista
        // Al eliminar una persona de la tabla, crear una nueva tabla sin esa persona.

    }

    public void crearTablaListaEspera(){

        // Creamos nueva tabla.

    }
    public void añadirPacienteLE(){

        // Añadimos Paciente a la tabla.
    }
    public void eliminarPersona(){

        // Elimina personade la tabla.
        // Crea una nueva con los datos actualizados.
    }

    /**
     * Exemple de PieChart
     * Quants pacients homes i quants pacients dones hi ha
     * @param event
     */
    public void btnChart(MouseEvent event) {
        idPieChart.getData().clear();
        idPieChart1.getData().clear();
        idPieChart2.getData().clear();
//        long edat = pacientList.stream()
//                .filter(pacient -> pacient.getGenere()== Persona.Genere.DONA)
//                .count();

        if (event.getClickCount() == 1) {
            Map<Integer, Integer> edatMap = new HashMap<Integer, Integer>();
            Map<Float, Integer> pesMap = new HashMap<Float, Integer>();
            Map<Integer, Integer> alcada = new HashMap<Integer, Integer>();

            for(Pacient p: pacientList) {
                if(edatMap.containsKey(p.getEdat())) {
                    edatMap.put(p.getEdat(), edatMap.get(p.getEdat())+1);

                }else {
                    edatMap.put(p.getEdat(),1);
                }
            }
            idPieChart.setTitle("Edat");

            for (int key : edatMap.keySet()) {
                idPieChart.getData().add(new PieChart.Data(String.valueOf(key),edatMap.get(key)));
            }

            for(Pacient p: pacientList) {
                if(pesMap.containsKey(p.getPes())) {
                    pesMap.put(p.getPes(), pesMap.get(p.getPes())+1);

                }else {
                    pesMap.put(p.getPes(),1);
                }
            }
            idPieChart1.setTitle("PES");

            for (float key : pesMap.keySet()) {
                idPieChart1.getData().add(new PieChart.Data(String.valueOf(key),pesMap.get(key)));
            }


            for(Pacient p: pacientList) {
                if(alcada.containsKey(p.getAlçada())) {
                    alcada.put(p.getAlçada(), alcada.get(p.getAlçada())+1);

                }else {
                    alcada.put(p.getAlçada(),1);
                }
            }
            idPieChart2.setTitle("Alçada");

            for (int key : alcada.keySet()) {
                idPieChart2.getData().add(new PieChart.Data(String.valueOf(key),alcada.get(key)));
            }
        } else if (event.getClickCount() >=2){
            Map<String, Integer> rangEdat = new HashMap<String, Integer>();
            Map<String, Integer> rangPes = new HashMap<String, Integer>();
            Map<String, Integer> rangAlcada = new HashMap<String, Integer>();

            for(Pacient p: pacientList) {

                // Utilizamos Ternarias para comprobar si existe la key con valor. Si Hay valor sumas 1 sino estableces un valor
                // Pd: No deberia Harcodear Tanto

                if(p.getEdat() <= 20 ){
                    rangEdat.put("<=20" , rangEdat.containsKey("<=20") ? rangEdat.get("<=20")+1 : 1);
                } else if ( p.getEdat() <= 30 ) {
                    rangEdat.put("20-30", rangEdat.containsKey("20-30") ? rangEdat.get("20-30") + 1 : 1);
                } else if ( p.getEdat() <= 40 ) {
                    rangEdat.put("31-40", rangEdat.containsKey("31-40") ? rangEdat.get("31-40") + 1 : 1);
                } else if ( p.getEdat() <= 50 ) {
                    rangEdat.put("41-50", rangEdat.containsKey("41-50") ? rangEdat.get("41-50") + 1 : 1);
                } else if ( p.getEdat() <= 60 ) {
                    rangEdat.put("51-60", rangEdat.containsKey("51-60") ? rangEdat.get("51-60") + 1 : 1);
                } else if ( p.getEdat() >= 60 ) {
                    rangEdat.put(">=60", rangEdat.containsKey(">=60") ? rangEdat.get(">=60") + 1 : 1);
                }
            }
            idPieChart.setTitle("Rang Edat");

            for (String key : rangEdat.keySet()) {
                idPieChart.getData().add(new PieChart.Data(key + " = " + String.valueOf(rangEdat.get(key)),rangEdat.get(key)));
            }

            for(Pacient p: pacientList) {

                // Utilizamos Ternarias para comprobar si existe la key con valor. Si Hay valor sumas 1 sino estableces un valor
                // Pd: No deberia Harcodear Tanto

                if(p.getPes() <= 60 ){
                    rangPes.put("<=60" , rangPes.containsKey("<=60") ? rangPes.get("<=60")+1 : 1);
                } else if ( p.getPes() <= 80 ) {
                    rangPes.put("61-80", rangPes.containsKey("61-80") ? rangPes.get("61-80") + 1 : 1);
                } else if ( p.getPes() <= 100 ) {
                    rangPes.put("81-100", rangPes.containsKey("81-100") ? rangPes.get("81-100") + 1 : 1);
                } else if ( p.getPes() <= 120 ) {
                    rangPes.put("101-120", rangPes.containsKey("101-120") ? rangPes.get("101-120") + 1 : 1);
                } else if ( p.getPes() >= 120 ) {
                    rangPes.put(">120", rangPes.containsKey(">120") ? rangPes.get(">120") + 1 : 1);
                }
            }
            idPieChart1.setTitle("Rang Pes");

            for (String key : rangPes.keySet()) {
                idPieChart1.getData().add(new PieChart.Data(key + " = " + String.valueOf(rangPes.get(key)),rangPes.get(key)));
            }

            for(Pacient p: pacientList) {

                // Utilizamos Ternarias para comprobar si existe la key con valor. Si Hay valor sumas 1 sino estableces un valor
                // Pd: No deberia Harcodear Tanto

                if(p.getAlçada() <= 150 ){
                    rangAlcada.put("<=1.50" , rangAlcada.containsKey("<=1.50") ? rangAlcada.get("<=1.50")+1 : 1);
                } else if ( p.getAlçada() <= 160 ) {
                    rangAlcada.put("1.51-1.60", rangAlcada.containsKey("1.51-1.60") ? rangAlcada.get("1.51-1.60") + 1 : 1);
                } else if ( p.getAlçada() <= 170 ) {
                    rangAlcada.put("1.61-1.70", rangAlcada.containsKey("1.61-1.70") ? rangAlcada.get("1.61-1.70") + 1 : 1);
                } else if ( p.getAlçada() <= 180 ) {
                    rangAlcada.put("1.71-1.80",rangAlcada.containsKey("1.71-1.80") ? rangAlcada.get("1.71-1.80") + 1 : 1);
                } else if ( p.getAlçada() <= 190 ) {
                    rangAlcada.put("1.81-1.90", rangAlcada.containsKey("1.81-1.90") ? rangAlcada.get("1.81-1.90") + 1 : 1);
                } else if ( p.getAlçada() <= 200 ) {
                    rangAlcada.put("1.91-2.00", rangAlcada.containsKey("1.91-2.00") ? rangAlcada.get("1.91-2.00") + 1 : 1);
                } else if ( p.getAlçada() >= 200 ) {
                    rangAlcada.put("2.00", rangAlcada.containsKey("2.00") ? rangAlcada.get("2.00") + 1 : 1);
                }
            }
            idPieChart2.setTitle("Rang Alçada");

            for (String key : rangAlcada.keySet()) {
                idPieChart2.getData().add(new PieChart.Data(key + " = " + String.valueOf(rangAlcada.get(key)),rangAlcada.get(key)));
            }
        }
    }

    public static void setCsvFile(String csvFile) {
        ControllerLlista.csvFile = csvFile;
    }
}