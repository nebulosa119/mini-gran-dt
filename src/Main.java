import Controller.*;
import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;


public class Main {

    public static void main(String[] args) throws Team.PlayerExistsException {
//
        //AccountsManager ac = new AccountsManager();
        //ac.createAccount(new Administrator("juan"));
        //FileManager.writeObjectToFile(ac, Types.USER.fileName);
        Controller.main(new String[]{});
        /*String[] teamNames = new String[]{"Sonido Caracol", "Lincoln", "Matambre Reloaded", "C.A. Hay Combate", "Tu Marido", "Cerezas Inocentes", "Piraña", "La Vieja Señora", "Asfalten Kayen", "Ultimo Momento", "El Equipo de Carama", "FC Ronvodwhisky", "Tenedor Libre", "Herederos del Ñoqui", "Savio F.C.", "Pato Criollo", "Extra Brutt", "El Mago y su Jauria", "Colectivo San Juan", "El Nono Michelin", "Submarino Amarilo", "Jamaica Bajo Cero", "Los Borbotones", "No Manzana", "Corta el Pasto", "Furia FC", "La Vino Tinto", "Lineo B", "Te lo Juro por las Nenas", "La Nave Fulbo Clu"};
        String[] menNames = new String[]{"Agustin", "Alejo", "Bruno", "Santino", "Daniel", "Pablo", "Mateo ", "Manuel", "Leo", "Martin ", "Pedro", "Juan", "Martin", "Antonio"};
        String[] womenNames = new String[]{"Jimena","Milagros","Cristina","Camila","Rosario","Sofía","María","Lucía","Martina","Catalina","Elena","Emilia","Valentina","Paula","Zoe"};
        String[] surnames = new String[]{"Ponce", "Ledesma", "Castillo", "Vega", "Villalba", "Arias", "Navarro", "Barrios", "Soria", "Alvarado", "Lozano", "James", "Basualdo", "Vedoya", "Momesso", "Osimani", "Dorado", "Gomez", "Noni"};

        Tournament t1 = new Tournament("Liga Mayores",7);// 5 2 suplentes
        Tournament t2 = new Tournament("Liga Damas",8);// 7 3 suplentes
        Tournament t3 = new Tournament("Campeonato",16);// 11 5 suplentes

        ArrayList<String> men = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 18; j++) {
                men.add(menNames[i]+" "+surnames[j]);
            }
        }
        ArrayList<String> women = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 18; j++) {
                women.add(womenNames[i] + " " + surnames[j]);
            }
        }
        Collections.shuffle(men);
        Collections.shuffle(women);
        for (int i = 0; i < 10; i++) {
            Team newTeam = new Team(teamNames[i]);
            for (int j = 0; j < t2.getMaxPlayers(); j++) {
                Player newPlayer = new Player(women.get(j));
                System.out.println(women.get(j));
                try {
                    newTeam.add(newPlayer,t2.getMaxPlayers());
                } catch (Team.CompleteTeamException e) {
                    e.printStackTrace();
                }
            }
            t2.addTeam(newTeam);
        }
        for (int i = 10; i < 20; i++) {
            Team newTeam = new Team(teamNames[i]);
            for (int j = 0; j < t1.getMaxPlayers(); j++) {
                Player newPlayer = new Player(men.get(j));
                System.out.println(men.get(j));
                try {
                    newTeam.add(newPlayer,t1.getMaxPlayers());
                } catch (Team.CompleteTeamException e) {
                    e.printStackTrace();
                }
            }
            t1.addTeam(newTeam);
        }
        for (int i = 20; i < 30; i++) {
            Team newTeam = new Team(teamNames[i]);
            for (int j = 0; j < t3.getMaxPlayers(); j++) {
                Player newPlayer = new Player(men.get(j+t3.getMaxPlayers()));
                System.out.println(men.get(j+t3.getMaxPlayers()));
                try {
                    newTeam.add(newPlayer,t3.getMaxPlayers());
                } catch (Team.CompleteTeamException e) {
                    e.printStackTrace();
                }
            }
            t3.addTeam(newTeam);
        }
        Administrator admin = new Administrator("larana");
        admin.addTournament(t1);
        admin.addTournament(t2);
        admin.addTournament(t3);

        Controller.AccountsManager accountsManager = new Controller.AccountsManager();
        accountsManager.createAccount(admin);

        Controller.FileManager.serializeObject(accountsManager,"accounts.temp");
*/
    }
}
