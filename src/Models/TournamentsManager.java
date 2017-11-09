package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class  TournamentsManager {

    private static TournamentsManager instance;
    private HashMap<Administrator,ArrayList<Tournament>> tournaments;

    public TournamentsManager() {
        instance = this;
    }


}
