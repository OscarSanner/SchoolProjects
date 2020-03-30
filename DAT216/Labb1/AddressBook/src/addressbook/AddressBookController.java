
package addressbook;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat215.lab1.Presenter;


public class AddressBookController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Button newButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ListView listView;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postCodeField;
    @FXML
    private TextField cityField;


    Presenter presenter;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        presenter = new Presenter(listView, firstNameField, lastNameField, phoneField, emailField, addressField, postCodeField, cityField);
        presenter.init();
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                presenter.contactsListChanged();
            }
        });
        firstNameField.focusedProperty().addListener(new TextFieldListener(firstNameField));
        lastNameField.focusedProperty().addListener(new TextFieldListener(lastNameField));
        phoneField.focusedProperty().addListener(new TextFieldListener(phoneField));
        emailField.focusedProperty().addListener(new TextFieldListener(emailField));
        addressField.focusedProperty().addListener(new TextFieldListener(addressField));
        postCodeField.focusedProperty().addListener(new TextFieldListener(postCodeField));
        cityField.focusedProperty().addListener(new TextFieldListener(cityField));
    }

    @FXML
    protected void textFieldActionPerformed(ActionEvent evt){
        presenter.textFieldActionPerformed(evt);
    }

    @FXML
    protected void newButtonActionPerformed(ActionEvent event) {
        presenter.newContact();
    }

    @FXML
    protected void deleteButtonActionPerformed(ActionEvent event) {
        presenter.removeCurrentContact();
    }

    @FXML
    protected void openAboutActionPerformed(ActionEvent event) throws IOException {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("addressbook/resources/AddressBook");
        Parent root = FXMLLoader.load(getClass().getResource("address_book_about.fxml"), bundle);
        Stage aboutStage = new Stage();
        aboutStage.setScene(new Scene(root));
        aboutStage.setTitle(bundle.getString("about.title.text"));
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);
        aboutStage.showAndWait();
    }

    @FXML
    protected void closeApplicationActionPerformed(ActionEvent event) throws IOException {

        Stage addressBookStage = (Stage) menuBar.getScene().getWindow();
        addressBookStage.hide();
    }
    private class TextFieldListener implements ChangeListener<Boolean>{

        private TextField textField;

        public TextFieldListener(TextField textField){
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                presenter.textFieldFocusGained(textField);

            }
            else{
                presenter.textFieldFocusLost(textField);
            }
        }
    }


}


