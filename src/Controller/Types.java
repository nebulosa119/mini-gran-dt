package controller;

public enum Types {
    USER("accounts.temp", "Users"), ADMIN("accounts.temp","Admin");

    public String fileName;
    public String name;
    Types(String fileName, String name) {
        this.fileName = fileName;
        this.name = name;
    }
}
