package Controllers;

import Db.Actions.DBAddAnimal;
import Db.Actions.DBSelectAllTypes;
import Model.AnimalTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddNewAnimalController {

    @FXML
    private TextField nameField;

    @FXML
    private CheckBox sterelizedCheckBox;

    @FXML
    private TextField weightField;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<AnimalTypes> typeComboBox;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() {
        ObservableList<AnimalTypes> combo = FXCollections.observableArrayList();
        DBSelectAllTypes dbSelectAllTypes = new DBSelectAllTypes();
        dbSelectAllTypes.getAllTypes(combo);
        typeComboBox.setItems(combo);
        typeComboBox.setPromptText("Выберите тип");
        acceptButton.setOnAction(event -> {
            if (!nameField.getText().isEmpty() && !weightField.getText().isEmpty()
                && typeComboBox.getSelectionModel().getSelectedItem() != null
                && dateField.getValue() != null && dateField.getValue().isEqual(LocalDate.now()) || dateField.getValue().isBefore(LocalDate.now())) {

                DBAddAnimal dbAddAnimal = new DBAddAnimal();
                String nameText = nameField.getText().trim();
                int typeOfAnimal = typeComboBox.getSelectionModel().getSelectedItem().getId();
                double weightDouble = Double.parseDouble(weightField.getText().trim().replace(',', '.'));
                boolean IsSterelized = sterelizedCheckBox.isSelected();
                String datePicked = dateField.getValue().toString().replace("-", "");
                dbAddAnimal.addAnimal(nameText, typeOfAnimal, weightDouble, IsSterelized, datePicked);
                Stage stage = (Stage) acceptButton.getScene().getWindow();
                stage.setUserData(1);
                stage.close();
            }
        });
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
    }
}
