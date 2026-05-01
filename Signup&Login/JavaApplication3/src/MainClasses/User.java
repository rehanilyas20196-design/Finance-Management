package MainClasses;

public class User {

    private String fullName;
    private String email;
    private String username;
    private String passwordHash;
    private String bananaHash;
    private String cnic;
    private String phone;

    public User(String fullName,
                String email,
                String username,
                String passwordHash,
                String bananaHash,
                String cnic,
                String phone) {

        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.bananaHash = bananaHash;
        this.cnic = cnic;
        this.phone = phone;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getBananaHash() { return bananaHash; }
    public String getCnic() { return cnic; }
    public String getPhone() { return phone; }
}