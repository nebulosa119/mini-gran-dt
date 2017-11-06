package Controllers;

import Models.Team;
import Models.Tournament;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class UserViewController extends ViewController {

    private User user;

    UserViewController(MainApp controller, User user) {
        super(controller);
        this.user = user;
    }

    public Scene createMainWindow(){
        //creamos la lista para el usuario
        ListView<String> listView = createListView();
        //respectivos botones
        Button buttonEditTeam = new Button("Edit Your Team");
        buttonEditTeam.setMaxWidth(Double.MAX_VALUE);
        buttonEditTeam.setOnAction(event -> {
            // pido la seleccion EJ larana: copa mayores y seteo la ventana
            String selection = listView.getSelectionModel().getSelectedItem();
            if (selection == null) {
                setNextWindow(listView.getItems().get(0));
                addUserToTournamentList(listView.getItems().get(0));
            } else
                setNextWindow(selection);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(listView,buttonEditTeam);

        return new Scene(vBox);
    }

    private ListView<String> createListView(){
        //cargamos los torneos
        Map<String,Set<String>> tournamentsBinding = AccountsManager.getAllTournaments();
        ArrayList<String> allTournaments = new ArrayList<>();
        // creamos un bining EJ: larana: CopaJuveniles
        for (String adminName:tournamentsBinding.keySet()) {
            for (String tourName:tournamentsBinding.get(adminName)) {
                String bindingString = adminName + ": " + tourName;
                allTournaments.add(bindingString);
            }
        }
        //creamos el Observablelist
        final ObservableList<String> stringList =
                FXCollections.observableArrayList(allTournaments);
        final ListView<String> listView = new ListView<>(stringList);
        //armamos la lista
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return listView;
    }

    private String getAdminName(String bindingName){
        int index = bindingName.indexOf(':');
        return bindingName.substring(0,index-1);
    }

    private String getTournamentName(String bindingName){
        int index = bindingName.indexOf(':');
        return bindingName.substring(index+1);
    }

    private void setNextWindow(String selection){
        // agarro por separado el torneo y el admin
        String tourName = getTournamentName(selection);
        String adminName = getAdminName(selection);
        // pido el equipo del usuario
        Team team = AccountsManager.getUserTeam(tourName);
        // creo la escena de pedroV
        controller.setScene("teamManager");
    }

    private void addUserToTournamentList(String selection) {
        String tourName = getTournamentName(selection);
        String adminName = getAdminName(selection);

        AccountsManager.addUserToTournament(adminName,tourName);
    }

}
