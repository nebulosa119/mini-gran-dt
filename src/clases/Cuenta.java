package clases;

public abstract class Cuenta extends Identificable {
    protected String passwd;

    public Cuenta(String nombre, int id, String passwd) {
        super(nombre, id);
        this.passwd = passwd;
    }

    public String getPasswd() {
        return new String(passwd);
    }
}
