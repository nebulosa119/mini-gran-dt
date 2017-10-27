package Model;

import Controller.Controller;

public abstract class Model {
    private Controller controller;
    private Account account;

    public Model(Controller controller, Account account) {
        this.account = account;
        this.controller = controller;
    }

}
