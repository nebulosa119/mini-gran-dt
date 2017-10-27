package Controller;

import Model.Account;
import View.View;

public abstract class Controller {
    protected Account model;
    protected View view;

     Controller(Account model, View view) {
        this.model = model;
        this.view = view;
    }
}
