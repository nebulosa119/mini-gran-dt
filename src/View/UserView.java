package View;

import Controller.Controller;
import Model.Team;
import Model.Tournament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class UserView extends View {
    public UserView(Controller controller) {
        super(controller);
    }

    @Override
    public Scene createMainWindow(){
        //creamos la lista para el usuario
        ListView<String> listView = createListView();
        //respectivos botones
        Button buttonEditTeam = new Button("Edit Your Team");
        buttonEditTeam.setMaxWidth(Double.MAX_VALUE);
        buttonEditTeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // pido la seleccionEJ larana: copa mayores
                String selection = listView.getSelectionModel().getSelectedItem();
                // agarro por separado el torneo y el admin
                String tourName = getTournamentName(selection);
                String adminName = getAdminName(selection);
                // pido el equipo del usuario
                Team team = controller.getUserTeam(tourName);
                // creo la escena de pedroV
                Scene scene = createTeamManagerWindow(controller.getTournament(adminName,tourName), team);
                controller.setScene(scene);
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(listView,buttonEditTeam);

        return new Scene(vBox);
    }

    private ListView<String> createListView(){
        //cargamos los torneos
        Map<String,ArrayList<String>> tournamentsBinding = controller.getAllTournaments();
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

}
