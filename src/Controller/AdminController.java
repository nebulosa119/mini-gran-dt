package Controller;

import Controller.Controller;
import Model.Administrator;
import Model.Tournament;

import java.util.ArrayList;

public class AdminController{

    private Administrator admin;

    public AdminController(Administrator admin) {
        this.admin = admin;
    }

    public void start() {
        // aca se deberia abrir una ventana dando opcion de crear torneo o apretar algun torneo
        // si apreta crear torneo deberia abrir la ventana TeamWindow y aca se crea una instancia de TeamController que lo maneja
        // si apreta un torneo se abre una ventana (aca iria el controlador de esa ventana) con: refreshPlayers o addTeam
        // si apreta refreshPlayers se instancia el RefreshPropertiesController

        //CREO QUE SERIA MEJOR QUE CADA CONTROLADOR TENGA ADENTRO LAS INSTANCIAS DE LAS SIGUIENTES OPCIONES QUE SE ABREN
        //CUANDO SE APRETA ESA OPCION EN VEZ DE CREAR TODAS LAS INSTANCIAS ACA ADENTRO.
    }

}