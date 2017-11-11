package Models;

import Models.Exceptions.InsufficientFundsException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


// implementa comparable con respecto a los puntos para ordenarlos en cada tournament y cuando se muestra la lista de puntajes
// solo hay que recorrer la lista e imprimirlos
public class User extends Account{

    private static final long serialVersionUID = 1L;

    private UserExpenses expenses = new UserExpenses(this);
    private UserTeams userTeams = new UserTeams(this);

    private int points; /**Pa qué protected si no lo necesitamos acceder desde subclases de User?*/

    public User(String name) {
        super(name);
        points = 0;
    }

    public int getPoints() {
        return points;
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

    public boolean hasSigned(Tournament t) {
        return userTeams.isParticipating(t);
    }

    public UserTeams getUserTeams() {
        return userTeams;
    }
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        return super.equals(o);
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
        out.writeInt(points);
    }

    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        points = ois.readInt();
    }
}