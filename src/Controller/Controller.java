package src.Controller;

import src.Model.Account;
import src.View.View;

public abstract class Controller {
    protected Account model;
    protected View view;

     Controller(Account model, View view) {
        this.model = model;
        this.view = view;
    }
}
