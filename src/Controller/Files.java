package Controller;

public enum Files {
    USERS("users.temp"), ADMIN("admin.temp");

    public String fileName;
    Files(String fileName) {
        this.fileName = fileName;
    }
}
