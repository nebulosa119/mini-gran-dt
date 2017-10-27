package Controller;

public enum Types {
    USER("users.temp", "Users"), ADMIN("admin.temp","Admin");

    public String fileName;
    public String name;
    Types(String fileName, String name) {
        this.fileName = fileName;
        this.name = name;
    }
}
