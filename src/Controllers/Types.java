package Controllers;

public enum Types {
    USER("data.temp", "Users"), ADMIN("data.temp","Admin");

    public String fileName;
    public String name;
    Types(String fileName, String name) {
        this.fileName = fileName;
        this.name = name;
    }
}
