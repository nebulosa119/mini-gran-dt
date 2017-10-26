package clases;

import java.util.HashMap;

public class Usuario extends Cuenta {
    private HashMap<Integer, Equipo> equipos;

    public Usuario(String nombre, int dni, String passwd) {
        super(nombre, dni, passwd);
        this.equipos = new HashMap<>();
    }

    // si no se contiene al torneo, se lo agrega, caso contrario se pisa el equipo
    public void addEquipo(int idTorneo, Equipo e){
        if (idTorneo < 0)
            throw new IllegalArgumentException("Id del torneo debe ser >= 0");
        equipos.put(idTorneo, e);
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof Usuario))
            return false;
        Usuario usuario = (Usuario) o;
        return super.equals(usuario);
    }
    @Override
    public int hashCode(){
        int result = super.hashCode();
        result = result * 5;
        return result;
    }

}
