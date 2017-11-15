package Models;

import Models.Exceptions.InsufficientFundsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

// implementa comparable con respecto a los puntos para ordenarlos en cada tournament y cuando se muestra la lista de puntajes
// solo hay que recorrer la lista e imprimirlos

/**
 * Clase que representa un usuario que participa vendiendo y comprando jugadores a lo largo de un torneo
 */
public class UserDT extends Account{

    private static final long serialVersionUID = 1L;

    private UserWallet expenses = new UserWallet();
    private UserTeams userTeams = new UserTeams();

    /**
     * Constructor
     * @param name String que representa el nombre de la cuenta.
     */
    public UserDT(String name) {
        super(name);
    }

    /**
     * Método para conseguir los puntos de un jugador en un torneo.
     * @param t De tipo Tournament, representa el torneo
     */
    public int getPoints(Tournament t) {
        return userTeams.getUserPoints(t);
    }

    /**
     * Método para vender un jugador de un torneo en el que el jugador esté inscripto
     * @param t Representa el torneo
     * @param p Representa el jugador que quiere vender
     */
    public void sell(Tournament t , Player p) {
        expenses.sell(t, p);
        userTeams.removePlayer(t, p);
    }

    /**
     * Método para comprar un jugador en un torneo
     * @param t Representa el torneo
     * @param p Representa el jugador que quiere vender
     */
    public void buy(Tournament t, Player p) throws InsufficientFundsException{
        expenses.buy(t, p);
        userTeams.addPlayer(t, p);
    }

    /**
     * Método para inscribirse en un torneo en particular
     * @param t Representa el torneo en el cual desea inscribirse
     */
    public void signUp(Tournament t) {
        expenses.addNewFund(t);
        userTeams.addNewTeam(t);
    }

    /**
     * Método para obtener acceso al UserWallet del usuario
     * @return Devuelve el UserWallet del usuario
     */
    public UserWallet getExpenses() {
        return expenses;
    }

    /**
     * Método para saber si el usuario se ha inscripto en un torneo
     * @param t Representa el torneo
     */
    public boolean hasSigned(Tournament t) {
        return userTeams.isParticipating(t);
    }

    /**
     * Método para obtener la instancia de la clase de manejo de equipos de usuario UserTeams
     */
    public UserTeams getUserTeams() {
        return userTeams;
    }

    /**
     * Método que permite que el administrador de un torneo se comunique con el usuario para pasarle la información sobre los cambios
     * en las propiedades de los jugadores
     * @param tour El torneo en cuestión
     * @param propertiesMap Mapa de los nombres de los jugadores a las Propiedades.
     */
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