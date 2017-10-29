package Controller;
        import Model.Administrator;
        import Model.Tournament;

        import java.util.ArrayList;

public class AdminController extends Controller {

        private Administrator admin;

        public AdminController(Administrator admin) {
                this.admin = admin;
        }

        @Override
        public void start() {
                /**Desde aca se muestran los torneos en los que participan llamando, y por cada uno se instancia
                 * TournamentController**/
                admin.getTournaments();
        }

}
