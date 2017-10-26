package src.Model;

public abstract class Account extends Identifiable {
    protected String passwd;

    public Account(String name, int id, String passwd) {
        super(name, id);
        this.passwd = passwd;
    }

    public String getPasswd() {
        return new String(passwd);
    }
}
