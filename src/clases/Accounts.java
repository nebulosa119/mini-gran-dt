package clases;

import clases.exceptions.IdAlreadyUsedException;
import clases.exceptions.NombreExistenteException;

import java.util.Vector;

public class Accounts {
    private static Vector<Cuenta> cuentas = (Vector<Cuenta>)FileMananger.readObjectFromFile("cuentas.temp");

    public static void addUsuario(Usuario u) throws NombreExistenteException, IdAlreadyUsedException {
        add(u);
    }

    private static void add(Cuenta c) throws NombreExistenteException, IdAlreadyUsedException {
        if (cuentas == null)
            cuentas = new Vector<>();
        for (Cuenta aux :cuentas) {
            if (aux.getNombre().equals(c.getNombre())){
                throw new NombreExistenteException();
            }
            if (aux.getId() == c.getId()){
                throw new IdAlreadyUsedException();
            }
        }
        cuentas.add(c);
    }

    public static Cuenta getCuenta(String nombre){
        for (Cuenta aux: cuentas) {
            if (aux.getNombre().equals(nombre))
                return aux; // no se si retornar una copia o la misma,
        }
        return null;
    }

    public static boolean correctInfo(String nombre, String passwd){
        if (cuentas == null){
            cuentas = new Vector<>();
        }else {
            for (Cuenta aux: cuentas) {
                if (aux.getNombre().equals(nombre) && aux.getPasswd().equals(passwd))
                    return true;
            }
        }
        return false;

    }
}
