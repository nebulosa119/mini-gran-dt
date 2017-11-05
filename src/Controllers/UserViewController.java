package Controllers;

import Models.Team;
import Models.Tournament;
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

    private void createTeamManagerWindow(Tournament tournament, Team team) {
        MainApp.setScene("TeamManager");
    }

    private void setNextWindow(String selection){
        // agarro por separado el torneo y el admin
        String tourName = getTournamentName(selection);
        String adminName = getAdminName(selection);
        // pido el equipo del usuario
        Team team = AccountsManager.getUserTeam(tourName);
        // creo la escena de pedroV
        Scene scene = createTeamManagerWindow(AccountsManager.getTournament(adminName,tourName), team);
        controller.setScene(scene);
    }

    private void addUserToTournamentList(String selection) {
        String tourName = getTournamentName(selection);
        String adminName = getAdminName(selection);

        AccountsManager.addUserToTournament(adminName,tourName);
    }

}
