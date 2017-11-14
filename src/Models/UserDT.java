package Models;

import Models.Exceptions.InsufficientFundsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

// implementa comparable con respecto a los puntos para ordenarlos en cada tournament y cuando se muestra la lista de puntajes
// solo hay que recorrer la lista e imprimirlos
public class UserDT extends Account{

    private static final long serialVersionUID = 1L;

    private UserWallet expenses = new UserWallet();
    private UserTeams userTeams = new UserTeams();

    public UserDT(String name) {
        super(name);
    }

    public int getPoints(Tournament t) {
        return userTeams.getUserPoints(t);
    }

    public void sell(Tournament t , Player p) {
        expenses.sell(t, p);
        userTeams.removePlayer(t, p);
    }

    public void buy(Tournament t, Player p) throws InsufficientFundsException{
        expenses.buy(t, p);
        userTeams.addPlayer(t, p);
    }

    public void signUp(Tournament t) {
        expenses.addNewFund(t);
        userTeams.addNewTeam(t);
    }

    public UserWallet getExpenses() {
        return expenses;
    }

    public boolean hasSigned(Tournament t) {
        return userTeams.isParticipating(t);
    }

    public UserTeams getUserTeams() {
        return userTeams;
    }

    public void refreshPoints(Tournament tour, Map<String, Player.Properties> propertiesMap) {
        userTeams.refreshPoints(propertiesMap, tour);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof UserDT && super.equals(o);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = result * 5;
        return result;
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeObject(expenses);
        out.writeObject(userTeams);
    }

    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        expenses = (UserWallet)ois.readObject();
        userTeams = (UserTeams)ois.readObject();
    }
}