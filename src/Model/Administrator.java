package Model;


import java.util.List;

public class Administrator extends Account{
    private List<Tournament> tournaments;

    public Administrator(String name) {
        super(name);
    }

    public void refresh(Properties p , String tournament) {

    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }
}
