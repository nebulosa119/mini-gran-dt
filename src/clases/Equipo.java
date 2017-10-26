package clases;

import java.util.HashMap;

public class Equipo extends Identificable {
    private int maxJugadores;// maximma cantidad de jugadores incluyendo suplentes
    private HashMap<Integer,Jugador> jugadores;

    private static int IDCounter = 0;

    public Equipo(String nombre, int maxJugadores) {
        super(nombre,IDCounter++);
        if (maxJugadores < 1){
            throw new IllegalArgumentException("Cantidad de jugadores debe ser mayor a 1");
        }
        this.maxJugadores = maxJugadores;
        this.jugadores = new HashMap<Integer,Jugador>();
    }

    public void add(Jugador j, int posicion) throws EquipoCompletoException, PosicionOcupadaExcepetion, JugadorYaExisteException{
        if (posicion < 1 || posicion > maxJugadores)
            throw new IllegalArgumentException("Posicion ebe ser entre 1 y el maximo de jugadores permitidos.");
        if (jugadores.size() >= maxJugadores)
            throw new EquipoCompletoException();
        if (jugadores.containsKey(posicion))
            throw new PosicionOcupadaExcepetion();
        if (jugadores.containsValue(j))
            throw new JugadorYaExisteException();
        jugadores.put(posicion,j);
    }

    public void remove(int posicion){
        jugadores.remove(posicion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Equipo))
            return false;
        Equipo equipo = (Equipo) o;
        return super.equals(equipo);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 2; // para diferenciarlos
        return result;
    }

    private class JugadorYaExisteException extends Exception {
        public JugadorYaExisteException() {
            super("El jugador ya se encuentra en el equipo");
        }
    }
    private class PosicionOcupadaExcepetion extends Exception {
        public PosicionOcupadaExcepetion() {
            super("La posicion ya se ecuentra ocupada en este equipo");
        }
    }
    private class EquipoCompletoException extends Exception {
        public EquipoCompletoException() {
            super("El equipo esta completo");
        }
    }
}
