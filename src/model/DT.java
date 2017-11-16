package model;

import model.exceptions.CompleteTeamException;
import model.exceptions.ExistentNameException;
import model.exceptions.InsufficientFundsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Clase que representa un usuario que participa vendiendo y comprando jugadores a lo largo de un torneo.
 */
public class DT extends User {

    private static final long serialVersionUID = 1L;

    private DTWallet expenses = new DTWallet();
    private DTTeamsManager DTTeamsManager = new DTTeamsManager();

    /**
     * Constructor
     * @param name String que representa el nombre de la cuenta.
     */
    public DT(String name) {
        super(name);
    }

    /**
     * Método para conseguir los puntos de un jugador en un torneo.
     * @param t De tipo PhysicalTournament, representa el torneo.
     */
    public int getPoints(PhysicalTournament t) {
        return DTTeamsManager.getUserPoints(t);
    }

    /**
     * Método para vender un jugador de un torneo en el que el jugador esté inscripto.
     * @param t Representa el torneo.
     * @param p Representa el jugador que quiere vender.
     */
    public void sell(PhysicalTournament t , PhysicalPlayer p) {
        expenses.sell(t, p);
        DTTeamsManager.removePlayer(t, p);
    }

    /**
     * Método para comprar un jugador en un torneo.
     * @param t Representa el torneo.
     * @param p Representa el jugador que quiere vender.
     */
    public void buy(PhysicalTournament t, PhysicalPlayer p) throws InsufficientFundsException, CompleteTeamException, ExistentNameException{
        expenses.buy(t, p);
        DTTeamsManager.addPlayer(t, p);
    }

    /**
     * Método para inscribirse en un torneo en particular.
     * @param t Representa el torneo en el cual desea inscribirse.
     */
    public void signUp(PhysicalTournament t) {
        expenses.addNewFund(t);
        DTTeamsManager.addNewTeam(t);
    }

    /**
     * Método para obtener acceso al DTWallet del usuario.
     * @return Devuelve el DTWallet del usuario.
     */
    public DTWallet getExpenses() {
        return expenses;
    }

    /**
     * Método para saber si el usuario se ha inscripto en un torneo.
     * @param t Representa el torneo.
     */
    public boolean hasSigned(PhysicalTournament t) {
        return DTTeamsManager.isParticipating(t);
    }

    /**
     * Método para obtener la instancia de la clase de manejo de equipos de usuario DTTeamsManager.
     */
    public DTTeamsManager getDTTeamsManager() {
        return DTTeamsManager;
    }

    /**
     * Método que permite que el administrador de un torneo se comunique con el usuario para pasarle la información sobre los cambios
     * en las propiedades de los jugadores.
     * @param tour El torneo en cuestión.
     * @param propertiesMap Mapa de los nombres de los jugadores a las Propiedades.
     */
    public void refreshPoints(PhysicalTournament tour, Map<String, PhysicalPlayer.Properties> propertiesMap) {
        DTTeamsManager.refreshPoints(propertiesMap, tour);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof DT && super.equals(o);
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
        out.writeObject(DTTeamsManager);
    }

    private void readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        expenses = (DTWallet)ois.readObject();
        DTTeamsManager = (DTTeamsManager)ois.readObject();
    }
}