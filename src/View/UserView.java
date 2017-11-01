package View;

import Controller.MainApp;
import Model.Team;
import Model.Tournament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class UserView extends View {

    public UserView(MainApp controller) {
        super(controller);
    }

    @Override
    public Scene createMainWindow(){
        //creamos la lista para el usuario
        ListView<String> listView = createListView();
        //respectivos botones
        Button buttonEditTeam = new Button("Edit Your Team");
        buttonEditTeam.setMaxWidth(Double.MAX_VALUE);
        buttonEditTeam.setOnAction(event -> {
            // pido la seleccion EJ larana: copa mayores y seteo la ventana
            String selection = listView.getSelectionModel().getSelectedItem();
            setNextWindow(selection);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(listView,buttonEditTeam);

        return new Scene(vBox);
    }

    private ListView<String> createListView(){
        //cargamos los torneos
        Map<String,Set<String>> tournamentsBinding = controller.getAllTournaments();
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

    private Scene createTeamManagerWindow(Tournament tournament, Team team) {
        return new Scene(new TextField("hola"));
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
        Team team = controller.getUserTeam(tourName);
        // creo la escena de pedroV
        Scene scene = createTeamManagerWindow(controller.getTournament(adminName,tourName), team);
        controller.setScene(scene);
    }

}
