package Controller.AdminController;

import Controller.Controller;
import Model.Administrator;
import Model.Properties;
import View.RefreshPropertiesWindow;

public class RefreshPropertiesController {

    private Administrator admin;

    public RefreshPropertiesController(Administrator admin) {
        this.admin = admin;
    }

    public void start() {
        RefreshPropertiesWindow refresh = new RefreshPropertiesWindow(admin);
        //refresh.main(); llamar a que se cree la pantalla
        Properties p = refresh.getProperties(); //Esta chequeado que sean mayores a 0?
        // deberia get un array de properties para cada jugador? o llamamos a get properties en un for para cada player?


    }
}
