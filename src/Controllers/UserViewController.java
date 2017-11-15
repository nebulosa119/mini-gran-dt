package Controllers;

import Models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static javafx.application.Platform.exit;

/**
 * Controlador de la vista principal del usuario.
 *
 * @author pmommeso
 */
public class UserViewController implements Initializable{

    private RadioButton selectedButton = null;

    private Map<RadioButton, PhysicalTournament> map = new HashMap<>();

    @FXML
    private Accordion tournamentAccordion;
    @FXML
    private Button logoutButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label signUpLabel;
    @FXML
    private Pane userTeamView;

    /**
     * Permite al usuario desloguear
     */
    @FXML
    private void handleLogout(){
        MainApp.setScene("login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(Administrator administrator : AccountsManager.getAdmins()) {
            ToggleGroup tournamentGroup = new ToggleGroup();
            VBox tournamentBox = new VBox(10);
            tournamentBox.setPadding(new Insets(10));
            for(PhysicalTournament physicalTournament : administrator.getTournaments()) {
                physicalTournament.setAdministrator(administrator);
                RadioButton tButton = new RadioButton(physicalTournament.getName());
                map.put(tButton, physicalTournament);
                tournamentGroup.getToggles().add(tButton);
                tournamentBox.getChildren().add(tButton);
            }
            tournamentGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if(tournamentGroup.getSelectedToggle() != null &&
                            ((DT)AccountsManager.getSignedAccount()).hasSigned(map.get(tournamentGroup.getSelectedToggle()))) {
                        RadioButton selected = (RadioButton)tournamentGroup.getSelectedToggle();
                        PhysicalTournament aux = administrator.getTournament(map.get(selected).getName());
                        TeamManagerController.setPhysicalTournament(aux);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Views/teamManager.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            MainApp.createAlert("Error cargando archivo, intente de nuevo.").showAndWait();
                            exit();
                        }
                        logoutButton.setVisible(false);
                        userTeamView.getChildren().add(root);
                    } else {
                        signUpLabel.setVisible(true);
                        signUpButton.setVisible(true);
                        selectedButton = (RadioButton) tournamentGroup.getSelectedToggle();
                    }
                }
            });
            TitledPane tournamentPane = new TitledPane(administrator.getName(), tournamentBox);
            tournamentAccordion.getPanes().add(tournamentPane);
        }

        signUpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(selectedButton != null) {
                    ((DT)AccountsManager.getSignedAccount()).signUp(map.get(selectedButton));
                    map.get(selectedButton).getAdministrator().addUser(map.get(selectedButton).getName(), (DT) AccountsManager.getSignedAccount());
                    TeamManagerController.setPhysicalTournament(map.get(selectedButton));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/Views/teamManager.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        MainApp.createAlert("Error cargando archivo, intente de nuevo.").showAndWait();
                        exit();
                    }
                    logoutButton.setVisible(false);
                    userTeamView.getChildren().add(root);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Transacción Completada");
                    alert.setHeaderText("La transacción fue completada");
                    alert.setContentText("Ha pagado un monto de $200 para inscribirse");
                    alert.showAndWait();
                }
            }
        });

    }

}
