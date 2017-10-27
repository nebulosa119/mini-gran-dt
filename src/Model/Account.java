package Model;

public abstract class Account extends Identifiable {

    public Account(String name) {
        super(name);
    }
    public boolean isUser(){
        return this instanceof User;
    }
    public boolean isAdmin(){
        return this instanceof User;
    }

}
