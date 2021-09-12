package Controllers;

import Db.Actions.DBUpdateAnimal;
import Model.AnimalTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditChoosenAnimalController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField weightField;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<AnimalTypes> typeComboBox;

    @FXML
    private CheckBox sterelizedCheckBox;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    public TextField getWeightField() {
        return weightField;
    }

    public DatePicker getDateField() {
        return dateField;
    }

    public ComboBox<AnimalTypes> getTypeComboBox() {
        return typeComboBox;
    }

    public CheckBox getSterelizedCheckBox() {
        return sterelizedCheckBox;
    }

    public TextField getNameField() {
        return nameField;
    }

    @FXML
    void initialize() {
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
        acceptButton.setOnAction(event -> {
            if (!nameField.getText().isEmpty() && !weightField.getText().isEmpty()
                && typeComboBox.getSelectionModel().getSelectedItem() != null
                && dateField.getValue() != null) {

                Stage stage = (Stage) acceptButton.getScene().getWindow();
                DBUpdateAnimal dbUpdateAnimal = new DBUpdateAnimal();
                String nameText = nameField.getText().trim();
                int typeOfAnimal = typeComboBox.getSelectionModel().getSelectedItem().getId();
                double weightDouble = Double.parseDouble(weightField.getText().trim().replace(',', '.'));
                boolean IsSterelized = sterelizedCheckBox.isSelected();
                String datePicked = dateField.getValue().toString().replace("-", "");
                dbUpdateAnimal.addAnimal(Integer.parseInt(stage.getUserData().toString()), nameText, typeOfAnimal, weightDouble, IsSterelized, datePicked);
                stage.close();
            }
        });
    }
}
