package Controllers;

import java.awt.*;
import java.io.*;

import Db.Actions.DBGetExcelReport;
import javafx.scene.control.Button;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.HashMap;

import java.util.Optional;

import Db.Actions.DBSelectAllTypes;
import Model.Animal;
import Db.Actions.DBDeleteWhatSelect;
import Db.Actions.DBSelectAll;
import Model.AnimalTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class MainController {

    private ObservableList<Animal> Data = FXCollections.observableArrayList();

    @FXML
    private TableView<Animal> animalsTable;

    @FXML
    private TableColumn<Animal, String> ColumnID;

    @FXML
    private TableColumn<Animal, String> ColumnName;

    @FXML
    private TableColumn<Animal, String> ColumnType;

    @FXML
    private TableColumn<Animal, Double> ColumnWeight;

    @FXML
    private TableColumn<Animal, Boolean> ColumnSterilized;

    @FXML
    private TableColumn<Animal, LocalDate> ColumnDate;

    @FXML
    private Button createReportButton;

    @FXML
    private Button createExcelReportButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColumnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ColumnWeight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        ColumnSterilized.setCellValueFactory(new PropertyValueFactory<>("Sterilized"));
        ColumnDate.setCellValueFactory(new PropertyValueFactory<>("DateOfReceipt"));
        createReportButton.setOnAction(event -> {
            if (animalsTable.getSelectionModel().getSelectedIndex() >= 0) {
                createReport(animalsTable.getItems().get(animalsTable.getSelectionModel().getSelectedIndex()));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Животное не выбрано");
                alert.setContentText("Выберите в списке с животными необходимую ячейку для создания отчета!");
                alert.showAndWait();
            }
        });
        createExcelReportButton.setOnAction(event -> {
            DirectoryChooserDemo directoryChooserDemo = new DirectoryChooserDemo();
            Stage stage = (Stage) animalsTable.getScene().getWindow();
            try {
                directoryChooserDemo.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(directoryChooserDemo.getDir() !=null){
            String dirToSave = directoryChooserDemo.getDir() + "/Список_всех_животных_от_" + LocalDate.now() + ".xls";
            DBGetExcelReport.getExcelReport(dirToSave);}
        });
        initData();
    }

    @FXML
    private void deleteAnimal() {
        int selectedIndex = animalsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            DBDeleteWhatSelect dbDeleteWhatSelect = new DBDeleteWhatSelect();
            dbDeleteWhatSelect.delete(animalsTable.getItems().remove(selectedIndex).getId());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Животное не выбрано");
            alert.setContentText("Выберите в списке с животными необходимую ячейку для удаления!");
            alert.showAndWait();
        }
    }

    @FXML
    private void addAnimal() throws MalformedURLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("addDialog.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setOnHiding(event -> initData());
        stage.setUserData(0);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("Logo.png")));
        stage.setScene(new Scene(root));
        stage.setTitle("Добавление животного");
        stage.showAndWait();
        if (stage.getUserData().equals(1)) {
            showInformationAboutNewAnimal(animalsTable.getItems().get(animalsTable.getItems().size() - 1));
        }
    }

    @FXML
    private void editAnimal() throws MalformedURLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("editDialog.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (animalsTable.getSelectionModel().getSelectedIndex() >= 0) {
            Animal index = animalsTable.getItems().get(animalsTable.getSelectionModel().getSelectedIndex());
            ObservableList<AnimalTypes> typesAnimal = FXCollections.observableArrayList();
            DBSelectAllTypes dbSelectAllTypes = new DBSelectAllTypes();
            dbSelectAllTypes.getAllTypes(typesAnimal);

            EditChoosenAnimalController controller = loader.getController();

            controller.getNameField().setText(index.getName());
            controller.getTypeComboBox().setItems(typesAnimal);
            controller.getTypeComboBox().setPromptText("Выберите тип");
            controller.getWeightField().setText(Double.toString(index.getWeight()));
            controller.getDateField().setValue(index.getDateOfReceipt());
            controller.getSterelizedCheckBox().setSelected(index.isSterilized() == "Да" ? true : false);

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setOnHiding(event -> initData());
            stage.setUserData(index.getId());
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("Logo.png")));
            stage.setScene(new Scene(root));
            stage.setTitle("Изменение выбранной ячейки");
            stage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Животное не выбрано");
            alert.setContentText("Выберите в списке с животными необходимую ячейку для изменения!");

            alert.showAndWait();
        }
    }

    private void initData() {
        DBSelectAll connectionDB = new DBSelectAll();
        connectionDB.connect(Data);
        animalsTable.setItems(Data);
    }

    private void showInformationAboutNewAnimal(Animal newAnimalId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Уведомление о поступлении");
        alert.setHeaderText("Поступило новое животное!");
        alert.setContentText("Вы хотите создать отчет?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                createReport(newAnimalId);
            } catch (Exception ex) {
            }
        }
    }

    private void createReport(Animal newAnimalId) {
        try {
            DirectoryChooserDemo directoryChooserDemo = new DirectoryChooserDemo();
            Stage stage = (Stage) animalsTable.getScene().getWindow();
            directoryChooserDemo.start(stage);
            int animalIndex = animalsTable.getItems().indexOf(newAnimalId);
            Animal animalObject = animalsTable.getItems().get(animalIndex);
            String dirToSave = directoryChooserDemo.getDir().toString() + "/Отчет_" + animalObject.getName() + "_" + animalObject.getDateOfReceipt() + ".docx";
            XWPFDocument document = new XWPFDocument(getClass().getClassLoader().getResourceAsStream("replace.docx"));
            HashMap<String, String> forChangeDocx = new HashMap<>();
            forChangeDocx.put("id", String.valueOf(animalObject.getId()));
            forChangeDocx.put("name", animalObject.getName());
            forChangeDocx.put("type", animalObject.getType());
            forChangeDocx.put("weight", String.valueOf(animalObject.getWeight()));
            forChangeDocx.put("sterilized", animalObject.isSterilized());
            forChangeDocx.put("date", animalObject.getDateOfReceipt().toString());

            for (XWPFParagraph p : document.getParagraphs()) {
                for (XWPFRun r : p.getRuns()) {
                    String word = r.text();
                    if (forChangeDocx.containsKey(word)) {
                        String wordToReplace = word.equals("weight") ? forChangeDocx.get(word) + " кг" : forChangeDocx.get(word);
                        r.setText(wordToReplace, 0);
                    }
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(dirToSave);
            document.write(fileOutputStream);
            fileOutputStream.close();
            document.close();
            Desktop.getDesktop().print(new File(dirToSave));
        } catch (Exception e) {
        }
    }
}
