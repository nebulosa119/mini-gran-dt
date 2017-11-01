package model;

public abstract class Session {
    private Account session;

    public Session(Account session) {
        this.session = session;
    }
}
