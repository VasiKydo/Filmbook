package filmbook;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {

    @DatabaseField(generatedId = true)
    private int userID;
    @DatabaseField
    private String name;
    @DatabaseField
    private String surname;
    @DatabaseField(unique = true)
    private String email;
    @DatabaseField(unique = true)
    private String username;
    @DatabaseField
    private String password;

    public User() {}

    public User(String name, String surname, String email, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;

        //Xrhsimopoioume setter dioti 8a kanei to message digest
        setPassword(password);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = Api.stringToSHA256String(password);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString(){ //einai ka8ara mia debugging methodos.
        return String.format("Name: %s, Surname: %s, Email: %s, Username: %s", name, surname, email, username);
    }
}
