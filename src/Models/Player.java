package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// los jugadores no tendran una posicion fija, pueden variar a lo largo del tornero
// por ende a la hora de subir los datos, todos los jugadores seran capaces de recibir
// cualquier atributo.
public class Player implements Serializable{

    private final static int INITIAL_AMOUNT = 1000;
    private static final long serialVersionUID = 1L;

    private String name;
    private int price;
    private Properties properties;

    public Player(String name, int price, Properties properties) {
        this.name = name;
        this.price = price;
        this.properties = properties;
    }

    public Player(String name, Properties properties) {
        this(name,INITIAL_AMOUNT,properties);
    }

    public Player(String name) {
        this(name,new Properties());
    }

    public Player(String name, int price) {
        this(name,price,new Properties());
    }

    public String getName() {
        return name;
    }

    public int getPrice() { return price; }

    public int getCalculatedPrice() {
        return properties.calculatePrice();
    }

    public Properties getProperties() { return properties; }

    public int getRanking() {
        return properties.calculateRanking();
    }

    void refresh(Properties p) {
        properties.refresh(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name != null ? name.equals(player.name) : player.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode()*7 : 0;
    }

    @Override
    public String toString() {
        return name;
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeUTF(name);
        out.writeInt(price);
        out.writeObject(properties);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        name = ois.readUTF();
        price = ois.readInt();
        properties = (Properties) ois.readObject();
    }

    public static class Properties implements Serializable {

        private int normal_goals_scored;
        private int goals_scored_by_penalty_kick;
        private int penalty_catched;
        private int goals_scored_goalkeeper;
        private int yellow_cards;
        private int red_cards;
        private int goals_against;

        public Properties() {
            this.normal_goals_scored = 0;
            this.goals_scored_by_penalty_kick = 0;
            this.penalty_catched = 0;
            this.goals_scored_goalkeeper = 0;
            this.yellow_cards = 0;
            this.red_cards = 0;
            this.goals_against = 0;
        }

        public Properties(int normal_goals_scored, int goals_scored_by_penalty_kick, int penalty_catched, int goals_scored_goalkeeper, int yellow_cards, int red_cards, int goals_against) {
            this.normal_goals_scored = normal_goals_scored;
            this.goals_scored_by_penalty_kick = goals_scored_by_penalty_kick;
            this.penalty_catched = penalty_catched;
            this.goals_scored_goalkeeper = goals_scored_goalkeeper;
            this.yellow_cards = yellow_cards;
            this.red_cards = red_cards;
            this.goals_against = goals_against;
        }

        public int getPoints() {
            return normal_goals_scored+goals_scored_by_penalty_kick+penalty_catched+goals_scored_goalkeeper-yellow_cards-red_cards-goals_against;
        }

        public void setProperty(int index, int property) {
            switch (index) {
                case 0: normal_goals_scored = property;
                        break;
                case 1: goals_scored_by_penalty_kick = property;
                        break;
                case 2: penalty_catched = property;
                        break;
                case 3: goals_scored_goalkeeper = property;
                        break;
                case 4: yellow_cards = property;
                        break;
                case 5: red_cards = property;
                        break;
                case 6: goals_against = property;
                        break;
            }
        }

        public int calculateRanking() {
            int resp=0;
            resp += normal_goals_scored             * PropValues.normal_goals_scored.getpValue();
            resp += goals_scored_by_penalty_kick    * PropValues.goals_scored_by_penalty_kick.getpValue();
            resp += penalty_catched                 * PropValues.penalty_catched.getpValue();
            resp += goals_scored_goalkeeper         * PropValues.goals_scored_goalkeeper.getpValue();
            resp += yellow_cards                    * PropValues.yellow_cards.getpValue();
            resp += red_cards                       * PropValues.red_cards.getpValue();
            resp += goals_against                   * PropValues.goals_against.getpValue();
            return resp;
        }

        public int calculatePrice() {
            int resp = 0;
            resp += normal_goals_scored         * 100 * PropValues.normal_goals_scored.getUPricePerCent();
            resp += goals_scored_by_penalty_kick* 100 * PropValues.goals_scored_by_penalty_kick.getUPricePerCent();
            resp += penalty_catched             * 100 * PropValues.penalty_catched.getUPricePerCent();
            resp += goals_scored_goalkeeper     * 100 * PropValues.goals_scored_goalkeeper.getUPricePerCent();
            return resp;
        }

        void refresh(Properties p) {
            this.normal_goals_scored            += p.normal_goals_scored;
            this.goals_scored_by_penalty_kick   += p.goals_scored_by_penalty_kick;
            this.penalty_catched                += p.penalty_catched;
            this.goals_scored_goalkeeper        += p.goals_scored_goalkeeper;
            this.yellow_cards                   += p.yellow_cards;
            this.red_cards                      += p.red_cards;
            this.goals_against                  += p.goals_against;
        }

        @Override
        public String toString() {
            return "Properties{" +
                    "normal_goals_scored=" + normal_goals_scored +
                    ", goals_scored_by_penalty_kick=" + goals_scored_by_penalty_kick +
                    ", penalty_catched=" + penalty_catched +
                    ", goals_scored_goalkeeper=" + goals_scored_goalkeeper +
                    ", yellow_cards=" + yellow_cards +
                    ", red_cards=" + red_cards +
                    ", goals_against=" + goals_against +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Properties that = (Properties) o;

            if (normal_goals_scored != that.normal_goals_scored) return false;
            if (goals_scored_by_penalty_kick != that.goals_scored_by_penalty_kick) return false;
            if (penalty_catched != that.penalty_catched) return false;
            if (goals_scored_goalkeeper != that.goals_scored_goalkeeper) return false;
            if (yellow_cards != that.yellow_cards) return false;
            if (red_cards != that.red_cards) return false;
            return goals_against == that.goals_against;
        }

        @Override
        public int hashCode() {
            int result = normal_goals_scored;
            result = 31 * result + goals_scored_by_penalty_kick;
            result = 31 * result + penalty_catched;
            result = 31 * result + goals_scored_goalkeeper;
            result = 31 * result + yellow_cards;
            result = 31 * result + red_cards;
            result = 31 * result + goals_against;
            return result;
        }

        public void writeObject(ObjectOutputStream out) throws IOException{
            out.defaultWriteObject();
            out.writeInt(normal_goals_scored);
            out.writeInt(goals_scored_by_penalty_kick);
            out.writeInt(penalty_catched);
            out.writeInt(goals_scored_goalkeeper);
            out.writeInt(yellow_cards);
            out.writeInt(red_cards);
            out.writeInt(goals_against);
        }
        public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
            ois.defaultReadObject();
            normal_goals_scored          = ois.readInt();
            goals_scored_by_penalty_kick = ois.readInt();
            penalty_catched              = ois.readInt();
            goals_scored_goalkeeper      = ois.readInt();
            yellow_cards                 = ois.readInt();
            red_cards                    = ois.readInt();
            goals_against                = ois.readInt();
        }

        public enum PropValues{
            normal_goals_scored(20,40,0.35),
            goals_scored_by_penalty_kick(10,20,0.1),
            penalty_catched(20,40,0.2),
            goals_scored_goalkeeper(60,120,0.35),
            yellow_cards(-5,-10,0),
            red_cards(-10,-20,0),
            goals_against(-20,-40,0);

            int pValue;
            int uValue;
            double uPricePerCent;

            PropValues(int pValue,int uValue,double uPricePerCent) {
                this.pValue = pValue;
                this.uValue = uValue;
                this.uPricePerCent = uPricePerCent;
            }

            public int getpValue() {
                return pValue;
            }

            public int getuValue() {
                return uValue;
            }

            public double getUPricePerCent() {
                return uPricePerCent;
            }
        }
    }
}
