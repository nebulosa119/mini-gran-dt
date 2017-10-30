package View;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserView extends View {
    public UserView(Controller controller) {
        super(controller);
    }

    @Override
    public Scene createScene() {
        return new Scene(new VBox(new Text("hola")));
    }
}
